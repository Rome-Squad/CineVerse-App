package com.giraffe.presentation.details.screens.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.model.groupByRole
import com.giraffe.presentation.details.model.toCastUi
import com.giraffe.presentation.details.model.toCrewUi
import com.giraffe.presentation.details.model.toMovieUi
import com.giraffe.presentation.details.model.toReviewUI
import com.giraffe.presentation.details.model.toUi
import com.giraffe.presentation.details.screens.moviedetails.screen.MovieDetailsRoute
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
    MovieDetailsScreenState()
), MovieDetailsInteractionListener {
    private val movieID = savedStateHandle.toRoute<MovieDetailsRoute>().id

    init {
        loadMovieDetailsScreen()
    }

    override fun onShowAddToCollectionBottomSheet() {
        safeExecute(
            onSuccess = ::onIsLoggedInSuccess,
        ) {
            isLoggedInUseCase()
        }
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
        sendEffect(MovieDetailsEffect.NavigateUp)
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
            onError = ::onCreateCollectionFailure
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

    override fun onCancelCreateNewCollectionClick() {
        updateState {
            it.copy(
                collectionBottomSheet = MovieDetailsScreenState.CollectionBottomSheet.AddToCollection
            )
        }
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
            onSuccess = { addMovieToCollectionSuccess(it, collectionId) },
            onError = { strRes, isNetworkError ->
                addMovieToCollectionError(
                    strRes,
                    isNetworkError,
                    collectionId
                )
            }
        ) {
            addMovieToCollectionUseCase(collectionId, movieID)
        }
    }

    override fun onAddRateButtonClick() {
        safeExecute {
            if (isLoggedInUseCase()) {
                updateState { it.copy(isVisibleGiveStarsBottomSheet = false) }

                addRatingUseCase(
                    movieId = state.value.movie.id,
                    ratingValue = state.value.currentRating.toFloat()
                )
            } else {
                updateState { it.copy(isVisibleLoginBottomSheet = true) }
            }
        }
    }

    fun loadMovieDetailsScreen() {
        updateState {
            it.copy(
                isLoadingMovieDetails = true,
                isNetworkError = false,
                errorMessage = null
            )
        }
        loadMovieDetails(movieID)
        loadMoviePeople(movieID)
        loadMovieReviews(movieID)
        loadRecommendedMovie(movieID)
    }

    private fun addMovieToCollectionSuccess(isAdded: Boolean, collectionId: Int) {
        updateState {
            it.copy(
                isLoadingAddToCollection = false,
                collections = it.collections.map { collection ->
                    if (collection.id == collectionId) collection.copy(isLoading = false)
                    else collection
                }
            )
        }
    }

    private fun addMovieToCollectionError(
        collectionId: Int,
        isNetworkError: Boolean,
        errorMessage: Int
    ) {
        updateState {
            it.copy(
                isLoadingAddToCollection = false,
                collections = it.collections.map { collection ->
                    if (collection.id == collectionId) collection.copy(isLoading = false)
                    else collection
                },
                errorMessage = errorMessage,
                isNetworkError = isNetworkError
            )
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMovieDetailsSuccess,
            onError = ::loadMovieDetailsError
        ) {
            getMovieDetails(movieId)
        }
    }

    private fun loadMovieDetailsSuccess(movie: Movie) {
        updateState {
            it.copy(
                movie = movie.toMovieUi(),
                isLoadingMovieDetails = false
            )
        }
        loadMovieGenres(movie.genresID)
    }

    private fun loadMovieDetailsError(errorMsgRes: Int, isNetworkError: Boolean) {
        updateState {
            it.copy(
                isLoadingMovieDetails = false,
                errorMessage = errorMsgRes,
                isNetworkError = isNetworkError,
            )
        }
    }

    private fun loadMovieGenres(genresIds: List<Int>) {
        safeExecute(
            onSuccess = ::loadMovieGenresSuccess,
            onError = ::loadMovieGenresError
        ) {
            getMoviesGenresByIds(genresIds)
        }
    }

    private fun loadMovieGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                movieGenres = genres.map { genre -> genre.title },
                isLoadingMovieGenres = false
            )
        }
    }

    private fun loadMovieGenresError(error: Throwable) {

        sendEffect(MovieDetailsEffect.Error(error))
    }

    private fun loadRecommendedMovie(movieId: Int) {
        safeExecute(
            onSuccess = ::loadRecommendedMovieSuccess,
            onError = ::loadRecommendedMovieError
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
                isLoadingRecommendedMovies = false
            )
        }
    }

    private fun loadRecommendedMovieError(error: Throwable) {
        updateState { it.copy(isLoadingRecommendedMovies = false) }
        sendEffect(MovieDetailsEffect.Error(error))
    }

    private fun loadMoviePeople(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMoviePeopleSuccess,
            onError = ::loadMoviePeopleError
        ) {
            getPeopleByMovieId(movieId)
        }
    }

    private fun loadMoviePeopleSuccess(people: List<Person>) {
        val cast = people.filter { it.type == PersonType.CAST }.take(10)
        val crew = people.filter { it.type == PersonType.CREW }.take(10)
        val mappedCrew = crew.map { it.toCrewUi() }
        updateState {
            it.copy(
                isLoadingCast = false,
                cast = cast.map { cast -> cast.toCastUi() },
                crew = mappedCrew.groupByRole()
            )
        }
    }

    private fun loadMoviePeopleError(error: Throwable) {
        updateState { it.copy(isLoadingCast = false) }
        sendEffect(MovieDetailsEffect.Error(error))
    }

    private fun loadMovieReviews(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMovieReviewsSuccess,
            onError = ::loadMovieReviewsError
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
                isLoadingReviews = false,
                movieReviews = reviews.map { review -> review.toReviewUI() }
            )
        }
    }

    private fun loadMovieReviewsError(error: Throwable) {
        updateState { it.copy(isLoadingReviews = false) }
        sendEffect(MovieDetailsEffect.Error(error))
    }

    private fun onIsLoggedInSuccess(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            safeExecute(
                onSuccess = ::onGetCollectionsSuccess,
                onError = ::onGetCollectionsError
            ) {
                getCollectionsUseCase()
            }
        } else {
            updateState { it.copy(isVisibleLoginBottomSheet = true) }
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
                collections = collections.map { it.toUi() }
            )
        }
    }

    private fun onGetCollectionsError(error: Throwable) {
        updateState { it.copy(collectionBottomSheet = null) }
        sendEffect(MovieDetailsEffect.Error(error))
    }

    private fun onCreateCollectionSuccess(result: Unit) {
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


    private fun onCreateCollectionFailure(error: Throwable) {
        updateState { it.copy(newCollectionName = "") }
        sendEffect(MovieDetailsEffect.Error(error))
    }
}