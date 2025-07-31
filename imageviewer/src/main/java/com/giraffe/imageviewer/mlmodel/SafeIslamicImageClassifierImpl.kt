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
import javax.inject.Inject

class SafeIslamicImageClassifierImpl @Inject constructor(
    context: Context
) : SafeIslamicImageClassifier {

    // Interpreter to run the Model
    private val interpreterNsfwModel: Interpreter
    private val interpreterNsfwModelTmdbData: Interpreter

    init {
        // Load the TFLite model from assets
        val nsfwModel = loadModelFile(context, fileName = "nsfw_model.tflite")
        val nsfwModelTmdbData = loadModelFile(context, fileName = "nsfw_model_tmdb_data.tflite")
        interpreterNsfwModel = Interpreter(nsfwModel)
        interpreterNsfwModelTmdbData = Interpreter(
            nsfwModelTmdbData
        )
    }

    private fun classify(bitmap: Bitmap): Float {

        val resizedBitmap = bitmap.scale(224, 224)
        val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)

        val outputNsfwModel = Array(1) { FloatArray(1) }
        val outputNsfwModelTmdbData = Array(1) { FloatArray(1) }

        // Run both models
        interpreterNsfwModel.run(inputBuffer, outputNsfwModel)
        interpreterNsfwModelTmdbData.run(inputBuffer, outputNsfwModelTmdbData)

        val unsafeNsfwModel = outputNsfwModel[0][0]
        val unsafeNsfwModelTmdbData = outputNsfwModelTmdbData[0][0]

        val finalUnsafe = maxOf(unsafeNsfwModel, unsafeNsfwModelTmdbData)

        return finalUnsafe
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
        fileName: String = "optimized_model.tflite"
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
        val unsafeScore = classify(bitmap)
        return unsafeScore > 0.5f  // Threshold
    }
}