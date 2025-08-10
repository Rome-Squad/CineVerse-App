package com.giraffe.match.screen.videoPlayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.theme.Theme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerScreen(
    youtubeVideoId: String,
    onBackClick: () -> Unit
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .navigationBarsPadding()
            .systemBarsPadding()
            .statusBarsPadding()
    ) {
        AppBar(
            showBackButton = true,
            onBackButtonClick = onBackClick
        )
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                YouTubePlayerView(context = context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(youtubeVideoId, 0f)
                        }
                    })
                }
            }
        )
    }
}