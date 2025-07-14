package com.giraffe.imageviewer.islamicimageviewer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import com.giraffe.imageviewer.R
import okhttp3.OkHttpClient

@Composable
fun IslamicAppropriateImageViewer(
    imageUrl: String,
    modifier: Modifier = Modifier,
    placeHolderResId: Int = R.drawable.placeholder
) {
    val context = LocalContext.current
    var shouldBlur by remember { mutableStateOf(false) }

    LaunchedEffect(imageUrl) {
        /*val result = imageLoader.execute(request).drawable as? BitmapDrawable
        val bitmap = result?.bitmap

        if (bitmap != null) {
            val classifier = IslamicInappropriateImageClassifier(context)
            shouldBlur = classifier.isUnsafe(bitmap)
        }*/
    }
    /*
    LaunchedEffect(imageUrl) {
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        // convert to bitmap
        val result = imageLoader.execute(request).drawable as? BitmapDrawable
        val bitmap = result?.bitmap

        if (bitmap != null) {
            val classifier = IslamicInappropriateImageClassifier(context)
            shouldBlur = classifier.isUnsafe(bitmap)
        }
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .placeholder(placeHolderResId)
            .crossfade(true)
            .apply {
                if (shouldBlur) {
                    transformations(BlurTransformation(context, radius = 25f))
                }
            }
            .build(),
        contentDescription = stringResource(R.string.image_viewer),
        modifier = modifier
    )*/
    Box{
        Image(
            modifier = modifier,
            contentDescription = "cover",
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(imageUrl),
        )
        if (shouldBlur) Box(modifier = modifier.background(Color.White.copy(.5f)))
    }
}