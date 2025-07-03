package com.giraffe.imageviewer.classifier

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import org.tensorflow.lite.Interpreter
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class MustafaImageClassifier(
    context: Context
): InappropriateImageClassifier {


    private val interpreter: Interpreter
    private val labels = listOf("inappropriate", "appropriate")
    init {
        // Load the TFLite model from assets
        val modelFile = loadModelFile(context, "mustafa_islamic_safe_image_classifier.tflite")
        interpreter = Interpreter(modelFile)
    }

    fun classify(bitmap: Bitmap): Map<String, Float> {
        val resizedBitmap = createScaledBitmap(bitmap, 224, 224, true)
        val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)

        val output = Array(1) { FloatArray(labels.size) }
        interpreter.run(inputBuffer, output)

        return labels.zip(output[0].toList()).toMap()
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val buffer = ByteBuffer.allocateDirect(1 * 224 * 224 * 3 * 4)
        buffer.order(ByteOrder.nativeOrder())

        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val pixel = bitmap.getPixel(x, y)
                buffer.putFloat(((pixel shr 16 and 0xFF) / 255f)) // R
                buffer.putFloat(((pixel shr 8 and 0xFF) / 255f))  // G
                buffer.putFloat(((pixel and 0xFF) / 255f))        // B
            }
        }

        buffer.rewind()
        return buffer
    }

    private fun loadModelFile(context: Context, fileName: String): ByteBuffer {
        val assetFileDescriptor = context.assets.openFd(fileName)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    override fun isInappropriate(bitmap: Bitmap): Boolean {
        Log.d("TAG", "isInappropriate: ${classify(bitmap)}")
        val result = classify(bitmap)

        val safeScore = result["appropriate"]?: 1f
        val inappropriateScore = result["inappropriate"]?: 1f

        val isInappropriate = when {
            inappropriateScore > .7f -> true
            inappropriateScore >= safeScore -> true
            else -> false
        }
        return isInappropriate
    }
}