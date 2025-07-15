package com.giraffe.details.screens.moviedetails

import com.giraffe.details.screens.moviedetails.model.MovieUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.movies.entity.MovieReview
import com.giraffe.media.person.entity.Person

data class MovieDetailsScreenState(

    //movie details
    val movie: MovieUi = MovieUi(),
    val movieGenres: List<MovieGenre> = emptyList(),
    val cast: List<Person> = emptyList(),
    val crew: List<Person> = emptyList(),

    val recommendedMovies: List<Movie> = emptyList(),

    val movieReviews: List<MovieReview> = emptyList(),

    //loading states
    val isLoadingMovieDetails: Boolean = true,
    val isLoadingMovieGenres: Boolean = true,
    val isLoadingCast: Boolean = true,
    val isLoadingRecommendedMovies: Boolean = true,
    val isLoadingReviews: Boolean = true,

    //bottom sheets visibility
    val isVisibleAddToCollectionBottomSheet: Boolean = false,
    val isVisibleGiveStarsBottomSheet: Boolean = false,
    val isVisibleLoginBottomSheet: Boolean = false,

    val isLoggedIn: Boolean = false,
)
