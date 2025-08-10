package com.giraffe.presentation.explore.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.giraffe.presentation.explore.R
import java.util.Locale

class VoiceSearchHelper(
    private val context: Context,
    private val onResult: (String) -> Unit,
    private val onRmsChanged: ((Float) -> Unit)? = null,
    private val onError: ((String) -> Unit)? = null
) {

    private var speechRecognizer: SpeechRecognizer? = null

    fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            onError?.invoke(context.getString(R.string.speech_recognition_not_available_on_this_device))
            return
        }

        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        }


        val listener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {
                onRmsChanged?.invoke(rmsdB)
            }
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                val errorMsg = getErrorMessage(error)
                onError?.invoke(errorMsg)
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val result = matches?.firstOrNull() .orEmpty()
                onResult(result)
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        speechRecognizer?.setRecognitionListener(listener)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speak_to_search))
        }

        speechRecognizer?.startListening(intent)
    }

    fun destroy() {
        speechRecognizer?.stopListening()
        speechRecognizer?.cancel()
        speechRecognizer?.destroy()
        speechRecognizer = null
    }


    private fun getErrorMessage(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> context.getString(R.string.audio_recording_error)
            SpeechRecognizer.ERROR_CLIENT -> context.getString(R.string.client_side_error)
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> context.getString(R.string.insufficient_permissions)
            SpeechRecognizer.ERROR_NETWORK -> context.getString(R.string.network_error)
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> context.getString(R.string.network_timeout)
            SpeechRecognizer.ERROR_NO_MATCH -> context.getString(R.string.no_match_found)
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> context.getString(R.string.speech_recognizer_is_busy)
            SpeechRecognizer.ERROR_SERVER -> context.getString(R.string.server_error)
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> context.getString(R.string.no_speech_input)
            else -> context.getString(R.string.unknown_error)
        }
    }
}