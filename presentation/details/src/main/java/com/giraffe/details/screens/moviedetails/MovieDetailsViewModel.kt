package com.giraffe.details.screens.moviedetails

import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.screens.moviedetails.model.MovieUi
import com.giraffe.details.screens.moviedetails.model.groupByRole
import com.giraffe.details.screens.moviedetails.model.toCastUi
import com.giraffe.details.screens.moviedetails.model.toCrewUi
import com.giraffe.details.screens.moviedetails.model.toReviewUI
import com.giraffe.media.entity.Review
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.usecase.GetMovieDetailsUseCase
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetMovieReviewsUseCase
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.GetPeopleByMovieIdUseCase

class MovieDetailsViewModel(
    val getMovieDetails: GetMovieDetailsUseCase,
    val getMovieGenres: GetMovieGenresUseCase,
    val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    val getPeopleByMovieId: GetPeopleByMovieIdUseCase
) : BaseViewModel<MovieDetailsScreenState, MovieDetailsEffect>(
    MovieDetailsScreenState()
), MovieDetailsInteractionListener {

    //loading movie data
    fun loadMovieDetails(movieId: Int) {
        safeExecute(
            onSuccess = ::loadMovieDetailsSuccess,
            onError =   ::loadMovieDetailsError
        ) {
            getMovieDetails(movieId)
        }

    }

    private fun loadMovieDetailsSuccess(movie: Movie) {
        updateState {
            it.copy(
                movie = movie.MovieUi(),
                isLoadingMovieDetails = false
            )
        }
        loadMovieGenres(movie.genresID)
        loadMoviePeople(movie.id)
        loadMovieReviews(movie.id)
    }

    private fun loadMovieDetailsError(error: Throwable) {
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
                movieGenres = genres.map { genre-> genre.title },
                isLoadingMovieGenres = false
            )
        }
    }

    private fun loadMovieGenresError(error: Throwable) {

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
        val cast = people.filter { it.role == "Acting" }
        val crew = people.filter { it.role != "Acting" }
        val mappedCrew = crew.map { it.toCrewUi() }
        updateState {
            it.copy(
                isLoadingCast = false,
                cast = cast.map { it.toCastUi() },
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

    private fun loadMovieReviewsSuccess(reviews: List<Review>) {
        updateState {
            it.copy(
                isLoadingReviews = false,
                movieReviews = reviews.map{ it.toReviewUI() }
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
        sendEffect(MovieDetailsEffect.NavigateToReviews)
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

    override fun onCollectionClick() {
        TODO("Add movie to a collection is not yet implemented")
    }

    override fun onPersonClick(personId: Int) {
        TODO("PersonScreen is not yet implemented")
    }

    override fun onAddRatingClick() {
        TODO("Authentication not yet implemented")
    }

}