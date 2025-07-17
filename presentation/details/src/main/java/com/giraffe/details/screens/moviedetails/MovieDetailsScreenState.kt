package com.giraffe.details.screens.moviedetails

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.models.CastUi
import com.giraffe.details.screens.moviedetails.model.MovieUi
import com.giraffe.details.screens.moviedetails.model.ReviewUI

data class MovieDetailsScreenState(

    //movie details
    val movie: MovieUi = MovieUi(),
    val movieGenres: List<String> = emptyList(),
    val cast: List<CastUi> = emptyList(),
    val crew: Map<String, List<String>> = emptyMap(),

    val recommendedMovies: List<Poster> = emptyList(),

    val movieReviews: List<ReviewUI> = emptyList(),

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
