package com.giraffe.details.screens.castDetails

sealed interface CastDetailsEffect {
    data class Error(val error: Throwable) : CastDetailsEffect
}