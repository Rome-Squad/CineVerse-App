package com.giraffe.details.screens.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.groupByRole
import com.giraffe.details.models.toCastUi
import com.giraffe.details.models.toCrewUi
import com.giraffe.details.models.toMovieUi
import com.giraffe.details.models.toReviewUI
import com.giraffe.details.screens.moviedetails.screen.MovieDetailsRoute
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.AddMovieRatingUseCase
import com.giraffe.media.movies.usecase.GetMovieDetailsUseCase
import com.giraffe.media.movies.usecase.GetMovieReviewsUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.usecase.GetPeopleByMovieIdUseCase
import com.giraffe.user.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    val getMovieDetails: GetMovieDetailsUseCase,
    val getMoviesGenresByIds: GetMoviesGenresByIdsUseCase,
    val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    val getRecommendedMovie: GetRecommendedMovieUseCase,
    val getPeopleByMovieId: GetPeopleByMovieIdUseCase,
    val isLoggedInUseCase: IsLoggedInUseCase,
    savedStateHandle: SavedStateHandle,
    val addRatingUseCase: AddMovieRatingUseCase
) : BaseViewModel<MovieDetailsScreenState, MovieDetailsEffect>(
    MovieDetailsScreenState()
), MovieDetailsInteractionListener {
    private val movieID = savedStateHandle.toRoute<MovieDetailsRoute>().id

    init {
        loadMovieDetailsScreen()
    }

    override fun onShowAddToCollectionBottomSheet() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = true
            )
        }
    }

    override fun onAddToCollectionButtonClick() {
        if (!state.value.isLoggedIn) {
            updateState {
                it.copy(
                    isVisibleLoginBottomSheet = true
                )
            }
        } else {
            sendEffect(MovieDetailsEffect.NavigateToCollection)
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
        updateState {
            MovieDetailsScreenState()
        }
        loadMovieDetails(movieId)
    }

    override fun onGiveStarsCardClick() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = true
            )
        }
    }

    override fun onDismissAddToCollectionBottomSheet() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = false
            )
        }
    }

    override fun onDismissLoginBottomSheet() {
        updateState {
            it.copy(
                isVisibleLoginBottomSheet = false
            )
        }
    }

    override fun onDismissGiveStarsBottomSheet() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = false
            )
        }
    }

    override fun onCastCardClick(personId: Int) {
        sendEffect(
            MovieDetailsEffect.NavigateToCastDetails(
                personId = personId
            )
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

    override fun onCollectionClick() {
        sendEffect(MovieDetailsEffect.NavigateToCollection)
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
        loadRecommendedMovie(movieID, 1)
    }

    private fun loadMovieDetails(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMovieDetailsSuccess,
            onError = ::loadMovieDetailsError
        ) {
            val movie = getMovieDetails(movieId)
            movie
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

    private fun loadRecommendedMovie(movieId: Int, page: Int) {
        safeExecute(
            onSuccess = ::loadRecommendedMovieSuccess,
            onError = ::loadRecommendedMovieError
        ) {
            getRecommendedMovie(movieId = movieId, page = page)
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
        updateState {
            it.copy(
                isLoadingRecommendedMovies = false,
            )
        }
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
        updateState {
            it.copy(
                isLoadingCast = false,
            )
        }
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
        updateState {
            it.copy(
                isLoadingReviews = false,
            )
        }
        sendEffect(MovieDetailsEffect.Error(error))
    }
}