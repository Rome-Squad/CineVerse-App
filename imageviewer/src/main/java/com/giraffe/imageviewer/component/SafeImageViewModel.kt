package com.giraffe.imageviewer.component

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.giraffe.imageviewer.mlmodel.SafeIslamicImageClassifier
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SafeImageViewModel @Inject constructor(
    private val classifier: SafeIslamicImageClassifier
) : ViewModel() {

    fun isImageUnsafe(bitmap: Bitmap): Boolean {
        return classifier.isUnsafe(bitmap)
    }
}