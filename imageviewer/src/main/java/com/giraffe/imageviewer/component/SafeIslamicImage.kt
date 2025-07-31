package com.giraffe.imageviewer.component


import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.giraffe.imageviewer.model.SafeIslamicImageHost

@NonRestartableComposable
@Composable
fun SafeIslamicImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
    alignment: Alignment = Alignment.Center
) {

    val context = LocalContext.current
    val host = remember { SafeIslamicImageHost(context.applicationContext) }

    LaunchedEffect(imageUrl) {
        host.loadImage(imageUrl, context)
    }

    Box {
        AsyncImage(
            model = host.imageState,
            contentDescription = contentDescription,
            contentScale = contentScale,
            alignment = alignment,
            modifier = modifier
        )
    }
}