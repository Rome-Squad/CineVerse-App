package com.giraffe.imageviewer.islamicimageviewer

import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giraffe.imageviewer.R
import com.giraffe.imageviewer.classifier.IslamicInappropriateImageClassifier
import com.giraffe.imageviewer.utils.BlurTransformation

@Composable
fun IslamicAppropriateImageViewer(
    imageUrl: String,
    modifier: Modifier = Modifier,
    placeHolderResId: Int = R.drawable.placeholder
) {
    val context = LocalContext.current
    var shouldBlur by remember { mutableStateOf(false) }

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
            shouldBlur = classifier.isInappropriate(bitmap)
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
    )
}