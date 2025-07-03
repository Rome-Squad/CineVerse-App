package com.giraffe.imageviewer.classifier

import android.graphics.Bitmap

class FakeClassifier: InappropriateImageClassifier {
    override fun isInappropriate(bitmap: Bitmap): Boolean {
        return false
    }
}