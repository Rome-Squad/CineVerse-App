package com.giraffe.imageviewer.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import coil.size.Size
import coil.transform.Transformation

class BlurTransformation(
    private val context: Context,
    private val radius: Float = 25f
) : Transformation {

    override val cacheKey: String = "BlurTransformation(radius=$radius)"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val config = input.config ?: Bitmap.Config.ARGB_8888
        val output = Bitmap.createBitmap(
            input.width,
            input.height, config
        )
        val canvas = Canvas(output)
        val paint = Paint()
        paint.isAntiAlias = true

        val blurred = try {
            BlurUtils.blur(context, input.copy(config, true), radius.toInt())
        } catch (e: Exception) {
            input
        }

        canvas.drawBitmap(blurred, 0f, 0f, paint)
        return output
    }
}