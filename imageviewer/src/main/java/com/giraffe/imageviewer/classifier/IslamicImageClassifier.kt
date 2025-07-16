package com.giraffe.imageviewer.classifier

import android.graphics.Bitmap

interface IslamicImageClassifier {
    fun isUnsafe(
        bitmap: Bitmap
    ): Boolean
}
