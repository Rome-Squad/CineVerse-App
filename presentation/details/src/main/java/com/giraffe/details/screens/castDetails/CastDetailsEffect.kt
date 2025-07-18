package com.giraffe.details.screens.castDetails

sealed interface CastDetailsEffect {
    data class Error(val error: Throwable) : CastDetailsEffect
    data class OpenUrl(val url: String) : CastDetailsEffect
}