package com.giraffe.details.screens.moviedetails

import androidx.compose.runtime.Stable
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.models.CastUi
import com.giraffe.details.models.CollectionUi
import com.giraffe.details.models.MovieUi
import com.giraffe.details.models.ReviewUI

@Stable
data class MovieDetailsScreenState(
    val movie: MovieUi = MovieUi(),
    val movieGenres: List<String> = emptyList(),
    val cast: List<CastUi> = emptyList(),
    val crew: Map<String, List<String>> = emptyMap(),
    val collections: List<CollectionUi> = emptyList(),
    val recommendedMovies: List<Poster> = emptyList(),
    val movieReviews: List<ReviewUI> = emptyList(),
    val newCollectionName: String = "",
    //loading states
    val isLoadingMovieDetails: Boolean = true,
    val isLoadingMovieGenres: Boolean = true,
    val isLoadingCast: Boolean = true,
    val isLoadingRecommendedMovies: Boolean = true,
    val isLoadingReviews: Boolean = true,
    val isLoadingAddToCollection: Boolean = false,
    //bottom sheets visibility
    val collectionBottomSheet: CollectionBottomSheet? = null,
    val isVisibleGiveStarsBottomSheet: Boolean = false,
    val isVisibleLoginBottomSheet: Boolean = false,
    val isLoggedIn: Boolean = false,
    val currentRating: Int = 0,
    val errorMessage: Int? = null,
    val isNetworkError: Boolean = false,
) {
    @Stable
    sealed class CollectionBottomSheet {
        object AddToCollection : CollectionBottomSheet()
        object CreateCollection : CollectionBottomSheet()
        object NoCollections : CollectionBottomSheet()
    }
}