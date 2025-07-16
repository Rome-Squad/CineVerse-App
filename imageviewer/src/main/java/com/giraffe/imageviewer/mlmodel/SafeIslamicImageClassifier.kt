package com.giraffe.imageviewer.mlmodel

import android.graphics.Bitmap

interface SafeIslamicImageClassifier {
    fun isUnsafe(
        bitmap: Bitmap
    ): Boolean
}
