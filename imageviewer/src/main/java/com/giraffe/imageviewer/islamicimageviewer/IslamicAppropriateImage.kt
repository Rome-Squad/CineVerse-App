package com.giraffe.imageviewer.islamicimageviewer

import androidx.compose.runtime.Composable
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giraffe.imageviewer.classifier.IslamicInappropriateImageClassifier
import com.giraffe.imageviewer.utils.BlurTransformation

@Composable
fun IslamicAppropriateImageViewer(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var shouldBlur by remember { mutableStateOf(false) }

    LaunchedEffect(imageUrl) {
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = imageLoader.execute(request).drawable as? BitmapDrawable
        val bitmap = result?.bitmap

        if (bitmap != null) {
            val classifier = IslamicInappropriateImageClassifier(context)
            shouldBlur = classifier.isInappropriate(bitmap)
            Log.d("TAG", "IslamicAppropriateImageViewer: $shouldBlur")
        }
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .apply {
                if (shouldBlur) {
                    transformations(BlurTransformation(context, radius = 25f))
                }
            }
            .build(),
        contentDescription = "Image Viewer",
        modifier = modifier
    )
}