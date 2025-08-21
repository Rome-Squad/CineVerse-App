package com.giraffe.imageviewer.mlmodel

import android.graphics.Bitmap
import androidx.compose.runtime.Stable
import com.giraffe.imageviewer.model.StrengthLevel

@Stable
interface SafeIslamicImageClassifier {
    suspend fun isUnsafe(
        bitmap: Bitmap,
        imageUrl: String,
        strengthLevel: StrengthLevel
    ): Boolean

    fun getResultFromCache(imageUrl: String): Boolean?
}
