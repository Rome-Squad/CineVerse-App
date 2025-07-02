package com.giraffe.imageviewer.classifier

import android.graphics.Bitmap

interface InappropriateImageClassifier {
    fun isInappropriate(
        bitmap: Bitmap
    ): Boolean
}