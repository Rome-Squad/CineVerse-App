package com.giraffe.imageviewer.model

import android.graphics.Bitmap

sealed class ImageState {
    object Loading : ImageState()
    data class Success(val bitmap: Bitmap, val isBlurred: Boolean) : ImageState()
    data class Error(val throwable: Throwable) : ImageState()
}
