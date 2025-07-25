package com.giraffe.details.screens.moviedetails

import android.util.Log
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.groupByRole
import com.giraffe.details.models.toCastUi
import com.giraffe.details.models.toCrewUi
import com.giraffe.details.models.toMovieUi
import com.giraffe.details.models.toReviewUI
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMovieDetailsUseCase
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetMovieReviewsUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.SetMovieRecentUseCase
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.usecase.GetPeopleByMovieIdUseCase


class MovieDetailsViewModel(
    movieID: Int,
    val getMovieDetails: GetMovieDetailsUseCase,
    val getMovieGenres: GetMovieGenresUseCase,
    val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    val getRecommendedMovie: GetRecommendedMovieUseCase,
    val getPeopleByMovieId: GetPeopleByMovieIdUseCase,
    val setMovieRecentUseCase: SetMovieRecentUseCase
) : BaseViewModel<MovieDetailsScreenState, MovieDetailsEffect>(
    MovieDetailsScreenState()
), MovieDetailsInteractionListener {

    init {
        loadMovieDetails(movieID)
        loadRecommendedMovie(movieID, 1)
    }

    //loading movie data
    private fun loadMovieDetails(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMovieDetailsSuccess,
            onError = ::loadMovieDetailsError
        ) {

            val movie = getMovieDetails(movieId)
            Log.d("TAG ViewModel", "loadMovieDetails: $movie")
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
        loadMoviePeople(movie.id)
        loadMovieReviews(movie.id)
        saveToRecentViewed(movie)
    }

    private fun loadMovieDetailsError(error: Throwable) {
        error.printStackTrace()
        updateState {
            it.copy(
                isLoadingMovieDetails = false,
            )
        }
        sendEffect(MovieDetailsEffect.Error(error))
    }

    private fun loadMovieGenres(genresIds: List<Int>) {
        safeExecute(
            onSuccess = ::loadMovieGenresSuccess,
            onError = ::loadMovieGenresError
        ) {
            getMovieGenres(genresIds)
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
                recommendedMovies = recommendedSeries.map {
                    Poster(
                        id = it.id,
                        name = it.title,
                        imageUri = it.posterUrl.toString(),
                        rating = it.rating
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
            //TODO("Implement pagination instead of fixed page")
            getMovieReviewsUseCase(
                movieId = movieId,
                pageNumber = 1,
                pageSize = 20
            )
        }
    }

    private fun saveToRecentViewed(movie: Movie) {
        safeExecute {
            setMovieRecentUseCase(movie)
        }
    }

    private fun loadMovieReviewsSuccess(reviews: List<Review>) {
        Log.d("TAG", "loadMovieReviewsSuccess: $reviews")
        updateState {
            it.copy(
                isLoadingReviews = false,
                movieReviews = reviews.map { review -> review.toReviewUI() }
            )
        }
    }

    private fun loadMovieReviewsError(error: Throwable) {
        Log.d("TAG", "loadMovieReviewsFailure: $error")
        updateState {
            it.copy(
                isLoadingReviews = false,
            )
        }
        sendEffect(MovieDetailsEffect.Error(error))
    }


    //user interaction listeners
    override fun onAddToCollectionClick() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = true
            )
        }
    }

    override fun onCreateCollectionClick() {
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

    override fun onLoginClick() {
        sendEffect(MovieDetailsEffect.NavigateToLogin)
    }

    override fun onShowMoreMoviesClick() {
        sendEffect(MovieDetailsEffect.NavigateToMovies)
    }

    override fun onShowMoreReviewsClick() {
        sendEffect(MovieDetailsEffect.NavigateToReviews(state.value.movieReviews))
    }

    override fun onMovieClick(movieId: Int) {
        updateState {
            MovieDetailsScreenState()
        }
        loadMovieDetails(movieId)
    }

    override fun onGiveStarsClick() {
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

    override fun navigateToCastDetailsScreen(personId: Int) {
        sendEffect(
            MovieDetailsEffect.NavigateToCastDetails(
                personId = personId
            )
        )
    }

    override fun navigateToMovieRecommendation(movieId: Int, title: String) {
        sendEffect(MovieDetailsEffect.NavigateToMoviesRecommended(movieId, title))
    }

    override fun onCollectionClick() {
    }

    override fun onPersonClick(personId: Int) {
    }

    override fun onAddRatingClick() {
    }

}