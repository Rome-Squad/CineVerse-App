package com.giraffe.imageviewer.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

object BlurUtils {
    fun blur(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        val rs = RenderScript.create(context)
        val input = Allocation.createFromBitmap(
            rs,
            bitmap)
        val output = Allocation.createTyped(
            rs,
            input.type)
        val script = ScriptIntrinsicBlur.create(
            rs,
            Element.U8_4(rs)
        )
        script.setRadius(radius.coerceIn(1, 300).toFloat())
        script.setInput(input)
        script.forEach(output)
        output.copyTo(bitmap)
        rs.destroy()

        return bitmap
    }
}