package com.giraffe.imageviewer.component


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.Companion.DefaultTransform
import coil.imageLoader
import coil.request.ImageRequest
import com.giraffe.imageviewer.R
import com.giraffe.imageviewer.blur.BlurTransformer
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifier
import com.giraffe.imageviewer.utils.drawableToBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

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

    // Preserve classifier over recomposition and config changes
    val classifier: SafeIslamicImageClassifier by inject(SafeIslamicImageClassifier::class.java)

    // Use app-level Coil ImageLoader
    val imageLoader = LocalContext.current.imageLoader

    // Cache for unsafe images to avoid redundant network requests
    val unsafeCache = remember { mutableStateMapOf<String, Boolean>() }

    var isPlaceholder by remember { mutableStateOf(true) }
    var shouldBlur by remember { mutableStateOf(true) }
    LaunchedEffect(imageUrl) {
        isPlaceholder = true
        if (imageUrl.isBlank() || imageUrl == "null") {
            isPlaceholder = true
            return@LaunchedEffect
        }
        val cachedResult = unsafeCache[imageUrl]
        if (cachedResult != null) {
            shouldBlur = cachedResult
            return@LaunchedEffect
        }

        withContext(Dispatchers.Default) {
            try {
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .allowHardware(false)
                    .build()

                val result = imageLoader.execute(request)
                val drawable = result.drawable
                val bitmap = drawable?.let { drawableToBitmap(it) }

                shouldBlur = bitmap?.let { classifier.isUnsafe(it) } ?: false
                unsafeCache[imageUrl] = shouldBlur
                isPlaceholder = false
            } catch (_: Exception) {
                isPlaceholder = true
            }
        }
    }

    if (isPlaceholder) {
        placeHolder(modifier)
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .apply {
                    if (shouldBlur) {
                        transformations(BlurTransformer(context, radius = 35f))
                    }
                }
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            colorFilter = colorFilter,
            filterQuality = filterQuality,
            transform = transform,
            modifier = modifier
        )
    }
}
