package com.giraffe.details.screens.recommended

interface RecommendedEffect {
    data class Error(val error: Throwable) : RecommendedEffect

}