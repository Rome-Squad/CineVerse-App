package com.giraffe.imageviewer.classifier

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class InappropriateImageClassifierImpl(
    context: Context
): InappropriateImageClassifier {

    private val interpreter: Interpreter

    init {
        val model = loadModelFile(context, "classifier.tflite")
        interpreter = Interpreter(model)
    }

    private fun loadModelFile(context: Context, fileName: String): MappedByteBuffer {
        val assetFileDescriptor = context.assets.openFd(fileName)
        val fileChannel = assetFileDescriptor.createInputStream().channel
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.declaredLength
        )
    }

    fun classify(bitmap: Bitmap): String {
        val inputSize = 256
        val resized = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resized)

        val output = Array(1) { FloatArray(2) } // Output: [safe, nsfw]
        interpreter.run(tensorImage.buffer, output)

        val safe = output[0][0]
        val unsafe = output[0][1]
        Log.d("TAG", "safe: $safe, unsafe: $unsafe")

        val result = if (unsafe > safe) "unsafe" else "safe"
        Log.d("TAG", "isInappropriate: $result")
        return result
    }

    override fun isInappropriate(bitmap: Bitmap): Boolean {
        val result = classify(bitmap)
        return result != "safe"
    }
}