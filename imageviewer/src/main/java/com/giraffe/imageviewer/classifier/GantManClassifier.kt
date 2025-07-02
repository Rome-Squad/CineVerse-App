package com.giraffe.imageviewer.classifier

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import kotlin.math.pow
import kotlin.math.sqrt

class GantManClassifier(
    context: Context
): InappropriateImageClassifier {

    private val interpreter: Interpreter

    init {
        val model = loadModelFile(
            context,
            "nsfw_mobilenet_v2_140_224.tflite"
        )
        interpreter = Interpreter(model)

    }

    private fun loadModelFile(
        context: Context,
        modelName: String
    ): MappedByteBuffer {
        val assetFileDescriptor = context.assets.openFd(modelName)
        val fileInputStream = assetFileDescriptor.createInputStream()
        val fileChannel = fileInputStream.channel

        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.declaredLength
        )
    }

    override fun isInappropriate(
        bitmap: Bitmap
    ): Boolean {
        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        // Convert to normalized float32 tensor
        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resized)

        val inputBuffer = tensorImage.buffer

        // Output: [1, 5] float array
        val output = TensorBuffer.createFixedSize(intArrayOf(1, 5), DataType.FLOAT32)

        interpreter.run(inputBuffer, output.buffer.rewind())

        val result = output.floatArray
        val score1 = result.getOrNull(2) ?: 0f
        val score2 = result.getOrNull(0) ?: 0f
        val score3 = result.getOrNull(3) ?: 0f

        val neutral = result.getOrNull(1) ?: 0f
        val drawings = result.getOrNull(4) ?: 0f

        val totalNSFW = score1 + score2
        Log.d("TAG", "isInappropriate neutral: $neutral")
        Log.d("TAG", "isInappropriate drawings : $drawings")
        Log.d("TAG", "isInappropriate porn: $score1")
        Log.d("TAG", "isInappropriate sexy : $score2")
        Log.d("TAG", "isInappropriate hentai: $score3")
        Log.d("TAG", "isInappropriate total: $totalNSFW")

        val avg = result.average()
        val stdDev = sqrt(result.map { (it - avg).pow(2) }.average())

// If score2 is high but everything else is near-zero → suspect false positive
        val likelyFalsePositive = result[0] > 0.95f && stdDev < 0.04

        val isInappropriate = when {
            likelyFalsePositive -> false
            score1 > 0.5f -> true
            score3 > 0.5f -> true
            score2 > 0.7f && neutral < 0.04f -> true
            else -> false
        }

        return isInappropriate
    }
}