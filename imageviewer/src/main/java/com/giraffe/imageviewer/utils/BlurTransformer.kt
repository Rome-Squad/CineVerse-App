package com.giraffe.imageviewer.utils

import android.content.Context
import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation

class BlurTransformer(
    private val context: Context,
    private val radius: Float = 25f
) : Transformation {

    override val cacheKey: String = "BlurTransformation(radius=$radius)"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {

        return try {

            Blur.blur(
                context,
                input,
                radius.toInt()
            )
        } catch (_: Exception) {
            input
        }
    }
}
