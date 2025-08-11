package com.giraffe.presentation.details.screens.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.AddCollectionUseCase
import com.giraffe.media.collections.usecase.AddMovieToCollectionUseCase
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.AddMovieRatingUseCase
import com.giraffe.media.movie.usecase.GetMovieDetailsUseCase
import com.giraffe.media.movie.usecase.GetMovieReviewsUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.usecase.GetPeopleByMovieIdUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.model.MovieUi
import com.giraffe.presentation.details.navigation.routes.MovieDetailsRoute
import com.giraffe.presentation.details.utils.groupByRole
import com.giraffe.presentation.details.utils.toCastUi
import com.giraffe.presentation.details.utils.toCrewUi
import com.giraffe.presentation.details.utils.toUi
import com.giraffe.media.exception.NoInternetException
import com.giraffe.user.exception.NoInternetException as UserNoInternetException
import com.giraffe.user.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val getMoviesGenresByIds: GetMoviesGenresByIdsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getRecommendedMovie: GetRecommendedMovieUseCase,
    private val getPeopleByMovieId: GetPeopleByMovieIdUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val addRatingUseCase: AddMovieRatingUseCase,
    private val addMovieToCollectionUseCase: AddMovieToCollectionUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val addCollectionUseCase: AddCollectionUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MovieDetailsScreenState, MovieDetailsEffect>(
    MovieDetailsScreenState(
        movie = MovieUi(
            id = savedStateHandle.toRoute<MovieDetailsRoute>().id
        ),
    )
), MovieDetailsInteractionListener {

    init {
        state.value.movie.id.let {
            loadMovieDetailsScreen(it)
        }
    }


    private fun loadMovieDetailsScreen(movieID: Int = state.value.movie.id) {
        updateState {
            it.copy(
                isLoading = true,
                isNoInternet = false,
            )
        }

        loadMovieDetails(movieID)
        loadMoviePeople(movieID)
        loadMovieReviews(movieID)
        loadRecommendedMovie(movieID)
    }


    override fun onShowAddToCollectionBottomSheet() {
        executeIfLoggedIn(
            block = {
                safeExecute(
                    onSuccess = ::onGetCollectionsSuccess,
                    onError = ::onGetCollectionsError,
                    block = getCollectionsUseCase::invoke
                )
            },
            ifNotLoggedIn = {
                updateState {
                    it.copy(
                        isVisibleLoginBottomSheet = true
                    )
                }
            }
        )
    }


    override fun onCreateCollectionButtonClick() {
        updateState {
            it.copy(
                collectionBottomSheet = MovieDetailsScreenState.CollectionBottomSheet.CreateCollection
            )
        }
    }

    override fun onLoginButtonClick() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = false,
                isVisibleLoginBottomSheet = false
            )
        }
        sendEffect(MovieDetailsEffect.NavigateToLogin)
    }

    override fun onShowMoreReviewsTextClick() {
        sendEffect(MovieDetailsEffect.NavigateToReviews(state.value.movie.id))
    }

    override fun onMoviePosterClick(movieId: Int) {
        updateState { MovieDetailsScreenState() }
        loadMovieDetails(movieId)
    }

    override fun onGiveStarsCardClick() {
        updateState { it.copy(isVisibleGiveStarsBottomSheet = true) }
    }

    override fun onDismissAddToCollectionBottomSheet() {
        updateState { it.copy(collectionBottomSheet = null) }
    }

    override fun onDismissLoginBottomSheet() {
        updateState { it.copy(isVisibleLoginBottomSheet = false) }
    }

    override fun onDismissGiveStarsBottomSheet() {
        updateState { it.copy(isVisibleGiveStarsBottomSheet = false) }
    }

    override fun onCastCardClick(personId: Int) {
        sendEffect(
            MovieDetailsEffect.NavigateToCastDetails(personId = personId)
        )
    }

    override fun onShowMoreRecommendedMoviesTextClick(movieId: Int, title: String) {
        sendEffect(MovieDetailsEffect.NavigateToMoviesRecommended(movieId, title))
    }

    override fun onShowMoreReviewsTextClick(movieId: Int) {
        sendEffect(MovieDetailsEffect.NavigateToReviews(state.value.movie.id))
    }

    override fun onRateChange(rate: Int) {
        safeExecute {
            updateState { it.copy(currentRating = rate) }
        }
    }

    override fun onBackButtonClick() {
        sendEffect(MovieDetailsEffect.NavigateBack)
    }

    override fun onPlayButtonClick(url: String) {
        sendEffect(MovieDetailsEffect.NavigateToYouTubePlayer(url))
    }

    override fun onNewCollectionNameChange(name: String) {
        updateState { it.copy(newCollectionName = name) }
    }

    override fun onConfirmCreateNewCollectionClick() {
        safeExecute(
            onSuccess = ::onCreateCollectionSuccess,
            onError = ::onCreateCollectionError
        ) {
            addCollectionUseCase(
                collection = Collection(
                    id = 0,
                    name = state.value.newCollectionName,
                    description = "",
                    itemsCount = 0
                )
            )
        }
    }

    private fun onCreateCollectionSuccess(result: Unit) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false
            )
        }

        safeExecute(
            onSuccess = ::onGetCollectionsSuccess,
            onError = ::onGetCollectionsError
        ) {
            val collections = getCollectionsUseCase()
            updateState {
                it.copy(
                    collectionBottomSheet = MovieDetailsScreenState.CollectionBottomSheet.AddToCollection,
                    newCollectionName = ""
                )
            }
            collections
        }
    }

    private fun onGetCollectionsSuccess(collections: List<Collection>) {
        updateState {
            it.copy(
                collectionBottomSheet = if (collections.isNotEmpty()) {
                    MovieDetailsScreenState.CollectionBottomSheet.AddToCollection
                } else {
                    MovieDetailsScreenState.CollectionBottomSheet.NoCollections
                },
                collections = collections.map { collection -> collection.toUi() },
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun onGetCollectionsError(error: Throwable) {
        updateState { it.copy(collectionBottomSheet = null) }
        onError(error)
    }

    private fun onCreateCollectionError(error: Throwable) {
        updateState { it.copy(newCollectionName = "") }
        onError(error)
    }


    override fun onCancelCreateNewCollectionClick() {
        updateState {
            it.copy(
                collectionBottomSheet = MovieDetailsScreenState.CollectionBottomSheet.AddToCollection
            )
        }
    }

    override fun onRetryClick() {
        loadMovieDetailsScreen(state.value.movie.id)
    }

    override fun onCollectionClick(collectionId: Int) {
        updateState {
            it.copy(
                collections = it.collections.map { collection ->
                    if (collection.id == collectionId) collection.copy(isLoading = true)
                    else collection
                }
            )
        }

        safeExecute(
            onSuccess = { addMovieToCollectionSuccess(collectionId) },
            onError = { error ->
                onAddMovieToCollectionError(
                    collectionId,
                    error
                )
            }
        ) {
            addMovieToCollectionUseCase(collectionId, state.value.movie.id)
        }
    }


    private fun addMovieToCollectionSuccess(collectionId: Int) {
        updateState {
            it.copy(
                collections = it.collections.map { collection ->
                    if (collection.id == collectionId) collection.copy(isLoading = false)
                    else collection
                },
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun onAddMovieToCollectionError(
        collectionId: Int,
        error: Throwable
    ) {
        updateState {
            it.copy(
                collections = it.collections.map { collection ->
                    if (collection.id == collectionId) collection.copy(isLoading = false)
                    else collection
                },
            )
        }

        onError(error)
    }

    override fun onAddRateButtonClick() {

        executeIfLoggedIn(
            block = {
                updateState { it.copy(isVisibleGiveStarsBottomSheet = false) }

                safeExecute(
                    onError = ::onError
                ) {
                    addRatingUseCase(
                        movieId = state.value.movie.id,
                        ratingValue = state.value.currentRating.toFloat()
                    )
                }
            },
            ifNotLoggedIn = {
                updateState {
                    it.copy(
                        isVisibleLoginBottomSheet = true
                    )
                }
            }
        )
    }

    private fun loadMovieDetails(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMovieDetailsSuccess,
            onError = ::onError
        ) {
            getMovieDetails(movieId)
        }
    }

    private fun loadMovieDetailsSuccess(movie: Movie) {
        updateState {
            it.copy(
                movie = movie.toUi(),
                isLoading = false,
                isNoInternet = false
            )
        }
        loadMovieGenres(movie.genresID)
    }


    private fun loadMovieGenres(genresIds: List<Int>) {
        safeExecute(
            onSuccess = ::loadMovieGenresSuccess,
            onError = ::onError
        ) {
            getMoviesGenresByIds(genresIds)
        }
    }

    private fun loadMovieGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                movieGenres = genres.map { genre -> genre.title },
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun loadRecommendedMovie(movieId: Int) {
        safeExecute(
            onSuccess = ::loadRecommendedMovieSuccess,
            onError = ::onError
        ) {
            getRecommendedMovie(movieId = movieId, page = 1)
        }
    }

    private fun loadRecommendedMovieSuccess(recommendedSeries: List<Movie>) {
        updateState {
            it.copy(
                recommendedMovies = recommendedSeries.map { movie ->
                    Poster(
                        id = movie.id,
                        name = movie.title,
                        imageUri = movie.posterUrl.toString(),
                        rating = movie.rating
                    )
                },
                isLoading = false,
                isNoInternet = false
            )
        }
    }


    private fun loadMoviePeople(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMoviePeopleSuccess,
            onError = ::onLoadMoviePeopleError
        ) {
            getPeopleByMovieId(movieId)
        }
    }

    private fun onLoadMoviePeopleError(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(MovieDetailsEffect.Error(error))
    }

    private fun loadMoviePeopleSuccess(people: List<Person>) {
        val cast = people.filter { it.type == PersonType.CAST }.take(10)
        val crew = people.filter { it.type == PersonType.CREW }.take(10)
        val mappedCrew = crew.map(Person::toCrewUi)
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                cast = cast.map(Person::toCastUi),
                crew = mappedCrew.groupByRole()
            )
        }
    }

    private fun loadMovieReviews(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMovieReviewsSuccess,
            onError = ::onError
        ) {
            getMovieReviewsUseCase(
                movieId = movieId,
                pageNumber = 1
            )
        }
    }

    private fun loadMovieReviewsSuccess(reviews: List<Review>) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                movieReviews = reviews.map(Review::toUi)
            )
        }
    }



    private fun executeIfLoggedIn(
        block: () -> Unit,
        ifNotLoggedIn: () -> Unit
    ) {
        safeExecute(
            onSuccess = { isLoggedIn ->
                updateState {
                    it.copy(
                        isLoading = false,
                        isNoInternet = false
                    )
                }

                if (isLoggedIn) {
                    block()
                } else {
                    ifNotLoggedIn()
                }
            },
            onError = ::onError,
            block = isLoggedInUseCase::invoke
        )
    }

    private fun onError(error: Throwable) {
        val isNetworkError = error is NoInternetException || error is UserNoInternetException

        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = isNetworkError || it.isNoInternet
            )
        }

        sendEffect(MovieDetailsEffect.Error(error))
    }
}