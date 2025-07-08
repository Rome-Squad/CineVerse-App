package com.giraffe.imageviewer.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.core.graphics.scale

object BlurUtils {
    fun blur(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        val clampedRadius = radius.coerceIn(1, 25)

        // Downscale the image
        // Trick to increase blur effect

        val scaleFactor = 0.5f
        val scaledBitmap = bitmap.scale(
            (bitmap.width * scaleFactor).toInt(),
            (bitmap.height * scaleFactor).toInt()
        )

        // Apply RenderScript blur
        // It is a deprecated but needed for devices below API 31

        val rs = RenderScript.create(context)
        val input = Allocation.createFromBitmap(rs, scaledBitmap)
        val output = Allocation.createTyped(rs, input.type)
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

        script.setRadius(clampedRadius.toFloat())
        script.setInput(input)
        script.forEach(output)
        output.copyTo(scaledBitmap)
        rs.destroy()

        // Upscale the image again after blur (blurred, lower detail)

        return scaledBitmap.scale(bitmap.width, bitmap.height)
    }

}