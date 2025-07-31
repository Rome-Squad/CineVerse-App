package com.giraffe.imageviewer.mlmodel

import android.graphics.Bitmap
import androidx.compose.runtime.Stable

@Stable
interface SafeIslamicImageClassifier {
    suspend fun isUnsafe(
        bitmap: Bitmap,
        imageUrl: String
    ): Boolean

    fun getResultFromCache(imageUrl: String): Boolean?
}
