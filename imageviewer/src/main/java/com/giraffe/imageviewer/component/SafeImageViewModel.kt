package com.giraffe.imageviewer.component

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifier
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SafeImageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val classifier: SafeIslamicImageClassifier
) : ViewModel() {
    val imageState = MutableStateFlow<Bitmap?>(null)


//    fun loadImage(imageUrl: String) {
//        viewModelScope.launch {
//            try {
//                val request = ImageRequest.Builder(context)
//                    .data(imageUrl)
//                    .placeholder(R.drawable.placeholder)
//                    .allowHardware(false)
//                    .allowConversionToBitmap(true)
//                    .diskCacheKey(imageUrl)
//                    .dispatcher(Dispatchers.IO)
//                    .build()
//
//                val result = context.imageLoader.execute(request)
//                val drawable = result.drawable
//                val bitmap = drawableToBitmap(drawable!!) ?: return@launch
//
//                val shouldBlur = isImageUnsafe(bitmap)
//
//                if (!shouldBlur) imageState.update { bitmap }
//                else {
//                    val blurTransformer = BlurTransformer(context, 35f)
//                    val blurredImage =
//                        blurTransformer.transform(bitmap, Size(bitmap.width, bitmap.height))
//                    imageState.update { blurredImage }
//                }
//            } catch (e: Exception) {
////                imageState.update {
////                    context.getDrawable(R.drawable.placeholder)?.toBitmap()
////                }
//                throw e
//            }
//        }
//    }
//
//    private fun isImageUnsafe(bitmap: Bitmap): Boolean {
//        return classifier.isUnsafe(bitmap)
//    }
}