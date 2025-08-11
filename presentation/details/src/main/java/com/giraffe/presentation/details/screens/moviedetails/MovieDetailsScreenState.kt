package com.giraffe.presentation.details.screens.moviedetails

import androidx.compose.runtime.Stable
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.presentation.details.model.CastUi
import com.giraffe.presentation.details.model.CollectionUi
import com.giraffe.presentation.details.model.MovieUi
import com.giraffe.presentation.details.model.ReviewUI

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
    val isLoggedIn: Boolean = false,
    val currentRating: Int = 0,

    val collectionBottomSheet: CollectionBottomSheet? = null,
    val isVisibleGiveStarsBottomSheet: Boolean = false,
    val isVisibleLoginBottomSheet: Boolean = false,

    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,

    ) {
    @Stable
    sealed class CollectionBottomSheet {
        object AddToCollection : CollectionBottomSheet()
        object CreateCollection : CollectionBottomSheet()
        object NoCollections : CollectionBottomSheet()
    }
}