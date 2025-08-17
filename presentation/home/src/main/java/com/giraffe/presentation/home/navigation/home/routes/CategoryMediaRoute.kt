package com.giraffe.presentation.home.navigation.home.routes

import android.content.Context
import androidx.annotation.Keep
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.home.R
import com.giraffe.presentation.home.screen.category_media.CategoryMediaScreen
import kotlinx.serialization.Serializable

@Serializable
data class CategoryMediaRoute(
    val sectionType: CategoryMediaSectionType
)

@Keep
@Serializable
enum class CategoryMediaSectionType {
    RECENTLY_RELEASED,
    TOP_RATED_TV_SHOWS,
    UPCOMING_MOVIES,
    RECENTLY_VIEWED,
    MATCHES_YOUR_VIBES,
    LATE_NIGHT_THRILLS,
    FAMILY_NIGHT_PICKS,
    MIND_BENDING_STORIES,
    BASED_ON_TRUE_EVENTS,
    CINEMATIC_MASTERPIECE,
    FEEL_GOOD_PREFERENCES;

    fun getSectionTitle(context: Context): String {
        return when (this) {
            RECENTLY_RELEASED -> context.getString(R.string.recently_released)
            TOP_RATED_TV_SHOWS -> context.getString(R.string.top_rated_tv_shows)
            UPCOMING_MOVIES -> context.getString(R.string.upcoming_movies)
            RECENTLY_VIEWED -> context.getString(R.string.you_recent_viewed)
            MATCHES_YOUR_VIBES -> context.getString(R.string.matches_your_vibe)
            LATE_NIGHT_THRILLS -> context.getString(R.string.late_night_thrills)
            FAMILY_NIGHT_PICKS -> context.getString(R.string.family_night_picks)
            MIND_BENDING_STORIES -> context.getString(R.string.mind_bending_stories)
            BASED_ON_TRUE_EVENTS -> context.getString(R.string.based_on_true_events)
            CINEMATIC_MASTERPIECE -> context.getString(R.string.cinematic_masterpieces)
            FEEL_GOOD_PREFERENCES -> context.getString(R.string.feel_good_favorites)
        }
    }
}

fun NavController.navigateToCategoryMedia(sectionType: CategoryMediaSectionType) {
    navigate(CategoryMediaRoute(sectionType = sectionType))
}

fun NavGraphBuilder.categoryMediaRoute(
    onBackClick: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    composable<CategoryMediaRoute> {
        CategoryMediaScreen(
            onBackClick = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
        )
    }
}