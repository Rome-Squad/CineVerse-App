package com.giraffe.imageviewer.model

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.drawable.toBitmapOrNull
import coil.ImageLoader
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.Size
import com.giraffe.imageviewer.blur.BlurTransformer
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifier
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifierImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class SafeIslamicImageHost @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val classifier: SafeIslamicImageClassifier =
        SafeIslamicImageClassifierImpl(context)
    private val imageLoader: ImageLoader = context.imageLoader

    var imageState by mutableStateOf<ImageState>(ImageState.Loading)
        private set

    suspend fun loadImage(imageUrl: String, strengthLevel: StrengthLevel) =
        withContext(Dispatchers.Default) {
        try {
            if (imageUrl.isBlank() || imageUrl.lowercase() == "null") {
                throw Exception()
            }
            imageState = ImageState.Loading

            val cacheKey = "$imageUrl-${strengthLevel.name}"
            val cachedBlurState = classifier.getResultFromCache(cacheKey)

            val bitmap = if (cachedBlurState == true) {
                loadBlurredImage(imageUrl)
            } else {
                loadNormalImage(imageUrl)
            }

            bitmap?.let { loadedBitmap ->
                val isUnsafe =
                    cachedBlurState ?: isImageUnsafe(loadedBitmap, imageUrl, strengthLevel)

                if (isUnsafe && cachedBlurState != true) {
                    val blurredBitmap = applyBlur(loadedBitmap)
                    imageState = ImageState.Success(blurredBitmap, true)
                } else {
                    imageState = ImageState.Success(loadedBitmap, isUnsafe)
                }
            }

        } catch (e: Exception) {
            imageState = ImageState.Error(e)
        }
    }


    private suspend fun loadNormalImage(imageUrl: String): Bitmap? = withContext(Dispatchers.IO) {
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .allowConversionToBitmap(true)
            .memoryCacheKey(imageUrl)
            .diskCacheKey(imageUrl)
            .dispatcher(Dispatchers.IO)
            .build()

        imageLoader.execute(request).drawable?.toBitmapOrNull()
    }

    private suspend fun loadBlurredImage(imageUrl: String): Bitmap? = withContext(Dispatchers.IO) {
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .allowConversionToBitmap(true)
            .memoryCacheKey("${imageUrl}_blurred")
            .dispatcher(Dispatchers.IO)
            .transformations(BlurTransformer(context, BLUR_RADIUS))
            .build()

        imageLoader.execute(request).drawable?.toBitmapOrNull()
    }

    private suspend fun applyBlur(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        BlurTransformer(context, BLUR_RADIUS).transform(bitmap, Size(bitmap.width, bitmap.height))
    }

    private suspend fun isImageUnsafe(
        bitmap: Bitmap,
        imageUrl: String,
        strengthLevel: StrengthLevel
    ): Boolean =
        withContext(Dispatchers.Default) {
            classifier.isUnsafe(bitmap, imageUrl, strengthLevel)
        }

    companion object {
        private const val BLUR_RADIUS = 35f
    }
}
