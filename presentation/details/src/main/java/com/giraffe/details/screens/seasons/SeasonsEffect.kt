package com.giraffe.details.screens.seasons

sealed interface SeasonsEffect {
    data class Error(val error: Throwable) : SeasonsEffect
}