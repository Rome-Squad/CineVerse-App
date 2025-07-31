package com.giraffe.imageviewer.mlmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class SafeIslamicImageClassifierImpl @Inject constructor(
    context: Context
) : SafeIslamicImageClassifier {

    companion object {
        // Interpreter to run the Model
        @Volatile
        private var interpreterNsfwModel: Interpreter? = null

        @Volatile
        private var interpreterNsfwModelTmdbData: Interpreter? = null

        val cachedImages = ConcurrentHashMap<String, Boolean>()

        private fun getInterpreterNsfwModel(
            context: Context,
            fileName: String = "nsfw_model.tflite"
        ): Interpreter {
            return interpreterNsfwModel ?: synchronized(this) {
                val nsfwModel = loadModelFile(context, fileName = fileName)
                interpreterNsfwModel = Interpreter(nsfwModel)
                interpreterNsfwModel!!
            }
        }

        private fun getInterpreterNsfwModelTmdbData(
            context: Context,
            fileName: String = "nsfw_model_tmdb_data.tflite"
        ): Interpreter {
            return interpreterNsfwModel ?: synchronized(this) {
                val nsfwModelTmdbData =
                    loadModelFile(context, fileName = fileName)
                interpreterNsfwModelTmdbData = Interpreter(nsfwModelTmdbData)
                interpreterNsfwModelTmdbData!!
            }
        }

        private fun loadModelFile(
            context: Context,
            fileName: String
        ): ByteBuffer {

            val assetFileDescriptor = context.assets.openFd(fileName)
            val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
            inputStream.channel.use { fileChannel ->
                val startOffset = assetFileDescriptor.startOffset
                val declaredLength = assetFileDescriptor.declaredLength

                return fileChannel.map(
                    FileChannel.MapMode.READ_ONLY,
                    startOffset,
                    declaredLength
                )
            }
        }
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    init {
        // Load the TFLite model from assets
        coroutineScope.launch {
            val nsfwModelTmdbData = loadModelFile(context, fileName = "nsfw_model_tmdb_data.tflite")
            interpreterNsfwModel = Interpreter(nsfwModel)
            interpreterNsfwModelTmdbData = Interpreter(
                nsfwModelTmdbData
            )
        }
    }

    private suspend fun classify(
        bitmap: Bitmap
    ): Map<String, Float> {

        // rescale the image to be suitable for the model's expected input
        val resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 224, 224)

        // convert the bitmap to byte buffer
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

    override suspend fun isUnsafe(bitmap: Bitmap, imageUrl: String): Boolean {
        return getResultFromCache(imageUrl) ?: withContext(Dispatchers.Default) {
            val unsafeScore = classify(bitmap)
            val isSafe = unsafeScore > 0.5f  // Threshold
            cachedImages[imageUrl] = isUnsafe
            isSafe
        }
    }

    override fun getResultFromCache(imageUrl: String): Boolean? {
        return cachedImages[imageUrl]
    }
}