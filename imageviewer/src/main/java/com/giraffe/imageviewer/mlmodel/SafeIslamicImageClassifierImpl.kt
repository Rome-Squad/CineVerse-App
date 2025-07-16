package com.giraffe.imageviewer.mlmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.get
import androidx.core.graphics.scale
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class SafeIslamicImageClassifierImpl(
    context: Context
) : SafeIslamicImageClassifier {

    // Interpreter to run the Model
    private val interpreter: Interpreter
    private val labels = listOf("unsafe", "safe")

    init {
        // Load the TFLite model from assets
        val modelFile = loadModelFile(context)
        interpreter = Interpreter(modelFile)
    }

    fun classify(
        bitmap: Bitmap
    ): Map<String, Float> {

        // rescale the image to be suitable for the model's expected input
        val resizedBitmap = bitmap.scale(
            width = 224,
            height = 224
        )

        // convert the bitmap to byte buffer
        val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)

        //the output shape is Array<FloatArray(size = 2)>
        val output = Array(1) { FloatArray(labels.size) }

        // run the model
        interpreter.run(inputBuffer, output)

        // return map of the results <label: String, score: Float>
        return labels.zip(output[0].toList()).toMap()
    }

    private fun convertBitmapToByteBuffer(
        bitmap: Bitmap
    ): ByteBuffer {
        val buffer = ByteBuffer.allocateDirect(1 * 224 * 224 * 3 * 4)
        buffer.order(ByteOrder.nativeOrder())

        for (y in 0 until 224) {
            for (x in 0 until 224) {

                val pixel = bitmap[x, y]

                buffer.putFloat(((pixel shr 16 and 0xFF) / 255f))
                buffer.putFloat(((pixel shr 8 and 0xFF) / 255f))
                buffer.putFloat(((pixel and 0xFF) / 255f))
            }
        }

        buffer.rewind()
        return buffer
    }

    private fun loadModelFile(
        context: Context,
        fileName: String = "mustafa_islamic_safe_image_classifier.tflite"
    ): ByteBuffer {

        val assetFileDescriptor = context.assets.openFd(fileName)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel

        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength

        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            startOffset,
            declaredLength
        )
    }

    override fun isUnsafe(bitmap: Bitmap): Boolean {

        val result = classify(bitmap)

        val safeScore = result["safe"] ?: 1f
        val unsafeScore = result["unsafe"] ?: 1f

        val isUnsafe = when {
            unsafeScore > .25f -> true
            unsafeScore >= safeScore -> true
            else -> false
        }
        return isUnsafe
    }
}