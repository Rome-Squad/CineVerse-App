package com.giraffe.imageviewer.component


import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.Companion.DefaultTransform
import coil.request.ImageRequest
import com.giraffe.imageviewer.R
import com.giraffe.imageviewer.mlmodel.IslamicImageClassifierImpl
import com.giraffe.imageviewer.blur.BlurTransformer
import com.giraffe.imageviewer.utils.drawableToBitmap
import java.io.ByteArrayOutputStream

@Composable
fun SafeIslamicImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
    transform: (AsyncImagePainter.State) -> AsyncImagePainter.State = DefaultTransform,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    placeHolder: @Composable (modifier: Modifier) -> Unit = { modifier ->
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = stringResource(id = R.string.placeholder),
            modifier = modifier
        )
    }
) {

    val context = LocalContext.current
    var isPlaceholder by remember { mutableStateOf(false) }
    var shouldBlur by remember { mutableStateOf(true) }

    LaunchedEffect(imageUrl) {
        try {
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false)
                .listener(
                    onError = { request, throwable ->
                        isPlaceholder = true
                    }
                )
                .build()

            val imageLoader = ImageLoader(context)
            val result = imageLoader.execute(request)

            val drawable = result.drawable
            val bitmap = drawable?.let {
                drawableToBitmap(it)
            }

            if (bitmap != null) {

                val outputStream = ByteArrayOutputStream()
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100, outputStream
                )

                val classifier = IslamicImageClassifierImpl(context)
                shouldBlur = classifier.isUnsafe(bitmap)
            }

        } catch (_: Exception) {
            isPlaceholder = true
        }
    }

    if (isPlaceholder) {
        placeHolder(
            modifier
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .apply {
                    if (shouldBlur) {
                        transformations(
                            BlurTransformer(context, radius = 35f)
                        )
                    }
                }
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )

    }
}


