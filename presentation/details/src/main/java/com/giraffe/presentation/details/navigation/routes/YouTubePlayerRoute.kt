package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable


@Serializable
internal data class YouTubePlayerRoute(val youtubeVideoId: String)


fun NavController.navigateToYouTubePlayer(youtubeVideoId: String) {
    navigate(YouTubePlayerRoute(youtubeVideoId))
}

fun NavGraphBuilder.youTubePlayerRouteRoute(
    onBackClick: () -> Unit,
) {
    composable<YouTubePlayerRoute> {
        val youtubeVideoId = it.toRoute<YouTubePlayerRoute>().youtubeVideoId
        _root_ide_package_.com.giraffe.presentation.details.screens.videoPlayer.YouTubePlayerScreen(
            youtubeVideoId = youtubeVideoId,
            onBackClick = onBackClick
        )
    }
}