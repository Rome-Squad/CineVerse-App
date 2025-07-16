package com.giraffe.imageviewer.mlmodel

import android.graphics.Bitmap

interface IslamicImageClassifier {
    fun isUnsafe(
        bitmap: Bitmap
    ): Boolean
}
