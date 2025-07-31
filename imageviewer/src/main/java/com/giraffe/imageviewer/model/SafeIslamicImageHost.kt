package com.giraffe.imageviewer.model

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.drawable.toBitmapOrNull
import coil.ImageLoader
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.Size
import com.giraffe.imageviewer.R
import com.giraffe.imageviewer.blur.BlurTransformer
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifier
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifierImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Stable
class SafeIslamicImageHost(
    @ApplicationContext applicationContext: Context
) {
    private val classifier: SafeIslamicImageClassifier =
        SafeIslamicImageClassifierImpl(applicationContext)
    private val imageLoader: ImageLoader = applicationContext.imageLoader
    var imageState by mutableStateOf<Bitmap?>(null)
        private set

    var isBlurState by mutableStateOf(false)


    suspend fun loadImage(imageUrl: String, context: Context) {
        withContext(Dispatchers.Default) {
            try {
                isBlurState = classifier.getResultFromCache(imageUrl) ?: false
                Log.i("is blurred image", isBlurState.toString())
                val request = if (isBlurState) {
                    ImageRequest.Builder(context)
                        .data(imageUrl)
                        .placeholder(R.drawable.placeholder)
                        .allowHardware(false)
                        .allowConversionToBitmap(true)
                        .memoryCacheKey(imageUrl)
                        .dispatcher(Dispatchers.IO)
                        .transformations(BlurTransformer(context, 35f))
                        .build()
                } else {
                    ImageRequest.Builder(context)
                        .data(imageUrl)
                        .placeholder(R.drawable.placeholder)
                        .allowHardware(false)
                        .allowConversionToBitmap(true)
                        .memoryCacheKey(imageUrl)
                        .diskCacheKey(imageUrl)
                        .dispatcher(Dispatchers.IO)
                        .build()
                }
                val result = imageLoader.execute(request)
                val drawable = result.drawable
                val bitmap = drawable?.toBitmapOrNull() ?: return@withContext

                if (isBlurState) {
                    imageState = bitmap
                    return@withContext
                }

                isBlurState = isImageUnsafe(bitmap, imageUrl)

                if (!isBlurState) imageState = bitmap
                else {
                    val blurTransformer = BlurTransformer(context, 35f)
                    val blurredImage =
                        blurTransformer.transform(bitmap, Size(bitmap.width, bitmap.height))
                    imageState = blurredImage
                }
            } catch (e: Exception) {
                imageState = context.getDrawable(R.drawable.placeholder)?.toBitmapOrNull()
                throw e
            }
        }
    }

    private suspend fun isImageUnsafe(bitmap: Bitmap, imageUrl: String): Boolean {
        return classifier.isUnsafe(bitmap, imageUrl)
    }
}