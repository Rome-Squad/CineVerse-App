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
        @Volatile
        private var modelFile: ByteBuffer? = null

        @Volatile
        private var interpreter: Interpreter? = null
        val cachedImages = ConcurrentHashMap<String, Boolean>()


        private fun loadModelFile(
            context: Context,
            fileName: String = "mustafa_islamic_safe_image_classifier.tflite"
        ): ByteBuffer {
            return modelFile ?: synchronized(this) {
                val assetFileDescriptor = context.assets.openFd(fileName)
                val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
                inputStream.channel.use { fileChannel ->
                    val startOffset = assetFileDescriptor.startOffset
                    val declaredLength = assetFileDescriptor.declaredLength
                    val bytes = fileChannel.map(
                        FileChannel.MapMode.READ_ONLY,
                        startOffset,
                        declaredLength
                    )
                    interpreter = Interpreter(bytes)
                    bytes
                }
            }
        }
    }

    private val labels = listOf("unsafe", "safe")

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    init {
        // Load the TFLite model from assets
        coroutineScope.launch {
            val modelFile = loadModelFile(context)
            interpreter = Interpreter(modelFile)
        }
    }

    private suspend fun classify(
        bitmap: Bitmap
    ): Map<String, Float> {

        // rescale the image to be suitable for the model's expected input
        val resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 224, 224)

        // convert the bitmap to byte buffer
        val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)

        //the output shape is Array<FloatArray(size = 2)>
        val output = Array(1) { FloatArray(labels.size) }

        // run the model
        interpreter!!.run(inputBuffer, output)

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


    override suspend fun isUnsafe(bitmap: Bitmap, imageUrl: String): Boolean {
        return getResultFromCache(imageUrl) ?: withContext(Dispatchers.Default) {
            val result = classify(bitmap)

            val safeScore = result["safe"] ?: 1f
            val unsafeScore = result["unsafe"] ?: 1f

            val isUnsafe = when {
                unsafeScore > .25f -> true
                unsafeScore >= safeScore -> true
                else -> false
            }
            cachedImages[imageUrl] = isUnsafe
            isUnsafe
        }
    }

    override fun getResultFromCache(imageUrl: String): Boolean? {
        return cachedImages[imageUrl]
    }
}