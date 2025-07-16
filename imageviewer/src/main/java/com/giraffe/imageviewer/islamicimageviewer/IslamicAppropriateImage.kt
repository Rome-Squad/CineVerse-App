package com.giraffe.imageviewer.islamicimageviewer


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.createBitmap
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giraffe.imageviewer.R
import com.giraffe.imageviewer.classifier.IslamicInappropriateImageClassifier
import com.giraffe.imageviewer.utils.BlurTransformation
import java.io.ByteArrayOutputStream

@Composable
fun IslamicAppropriateImageViewer(
    imageUrl: String,
    modifier: Modifier = Modifier,
    placeHolderResId: Int = R.drawable.placeholder
) {
    val context = LocalContext.current
    var shouldBlur by remember { mutableStateOf(false) }
    LaunchedEffect(imageUrl) {
        try {
            val request = ImageRequest.Builder(context)

                .data(imageUrl)
                .allowHardware(false)
                .listener(
                    onStart = { request ->
                        Log.d("IslamicViewer", "Image load started: ${request.data}")
                    },
                    onSuccess = { request, metadata ->
                        Log.d("IslamicViewer", "Image load success: ${request.data}")
                    },
                    onError = { request, throwable ->
                        Log.e(
                            "IslamicViewer",
                            "Image load failed: ${request.data}, Exception: ${throwable.throwable.message}"
                        )
                    },
                    onCancel = { request ->
                        Log.w("IslamicViewer", "Image load cancelled: ${request.data}")
                    }
                )
                .build()

            val imageLoader = ImageLoader(context)
            val result = imageLoader.execute(request)
            Log.d("IslamicViewer", "Image loading result: $result")

            val drawable = result.drawable
            val bitmap = drawable?.let { drawableToBitmap(it) }

            if (bitmap != null) {
                Log.d("IslamicViewer", "Bitmap loaded successfully")

                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)

                Log.d("IslamicViewer", "Base64: ${base64.take(100)}...")

                val classifier = IslamicInappropriateImageClassifier(context)
                shouldBlur = classifier.isUnsafe(bitmap)
            } else {
                Log.e("IslamicViewer", "Failed to convert drawable to Bitmap.")
            }

        } catch (e: Exception) {
            Log.e("IslamicViewer", "Exception while decoding image", e)
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

/**
 * Converts any Drawable to Bitmap safely.
 */
fun drawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }

    val width = drawable.intrinsicWidth.takeIf { it > 0 } ?: 1
    val height = drawable.intrinsicHeight.takeIf { it > 0 } ?: 1

    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}