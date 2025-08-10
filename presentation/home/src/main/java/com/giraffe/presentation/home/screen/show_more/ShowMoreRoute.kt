package com.giraffe.presentation.home.screen.show_more

import android.content.Context
import androidx.annotation.Keep
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.home.R
import kotlinx.serialization.Serializable

@Serializable
data class ShowMoreRoute(
    val sectionType: ShowMoreSectionType
)

@Keep
@Serializable
enum class ShowMoreSectionType{
    RECENTLY_RELEASED,
    TOP_RATED_TV_SHOWS,
    UPCOMING_MOVIES,
    RECENTLY_VIEWED,
    MATCHES_YOUR_VIBES;

    fun getSectionTitle(context: Context): String {
        return when (this) {
            RECENTLY_RELEASED -> context.getString(R.string.recently_released)
            TOP_RATED_TV_SHOWS -> context.getString(R.string.top_rated_tv_shows)
            UPCOMING_MOVIES -> context.getString(R.string.upcoming_movies)
            RECENTLY_VIEWED -> context.getString(R.string.you_recent_viewed)
            MATCHES_YOUR_VIBES -> context.getString(R.string.matches_your_vibe)
        }
    }

}

fun NavController.navigateToShowMore(sectionType: ShowMoreSectionType) {
    navigate(
        ShowMoreRoute(
            sectionType = sectionType,
        )
    )
}

fun NavGraphBuilder.showMoreRoute(
    onBackClick: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    composable<ShowMoreRoute> {
        ShowMoreScreen(
            onBackClick = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
        )
    }
}