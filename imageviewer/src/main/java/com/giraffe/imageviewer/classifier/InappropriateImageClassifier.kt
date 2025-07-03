package com.giraffe.imageviewer.classifier

import android.graphics.Bitmap

interface InappropriateImageClassifier {
    fun isUnsafe(
        bitmap: Bitmap
    ): Boolean
}
