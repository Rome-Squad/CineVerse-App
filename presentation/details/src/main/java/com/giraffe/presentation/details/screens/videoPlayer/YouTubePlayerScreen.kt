package com.giraffe.presentation.details.screens.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.giraffe.presentation.details.base.BaseScreen
import com.giraffe.presentation.details.utils.hasInternet
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerScreen(
    youtubeVideoId: String,
    onBackClick: () -> Unit
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    var isNoInternet by rememberSaveable { mutableStateOf(!hasInternet(context)) }

    BaseScreen(
        title = "",
        isLoading = false,
        isNoInternet = isNoInternet,
        onBackClick = onBackClick,
        onRetryClick = {
            isNoInternet = !hasInternet(context)
        }
    ) {
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