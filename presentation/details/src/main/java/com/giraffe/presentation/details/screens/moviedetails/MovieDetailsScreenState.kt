package com.giraffe.presentation.details.screens.moviedetails

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.Stable
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.model.CastUi
import com.giraffe.presentation.details.model.CollectionUi
import com.giraffe.presentation.details.model.MovieUi
import com.giraffe.presentation.details.model.ReviewUI
import com.giraffe.user.entity.ContentPreference

@Stable
data class MovieDetailsScreenState(

    val movie: MovieUi = MovieUi(),
    val movieGenres: List<String> = emptyList(),
    val cast: List<CastUi> = emptyList(),
    val crew: Map<String, List<String>> = emptyMap(),
    val collections: List<CollectionUi> = emptyList(),
    val movieCollectionsIds: List<Int> = emptyList(),
    val recommendedMovies: List<Poster> = emptyList(),
    val movieReviews: List<ReviewUI> = emptyList(),
    val newCollectionName: String = "",
    val currentRating: Int = 0,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
    val collectionBottomSheet: CollectionBottomSheet? = null,
    val isVisibleGiveStarsBottomSheet: Boolean = false,
    val isVisibleLoginBottomSheet: Boolean = false,

    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,

    val animationProgress: Animatable<Float, AnimationVector1D> = Animatable(0f)
) {
    @Stable
    sealed class CollectionBottomSheet {
        object AddToCollection : CollectionBottomSheet()
        object CreateCollection : CollectionBottomSheet()
        object NoCollections : CollectionBottomSheet()
    }
}