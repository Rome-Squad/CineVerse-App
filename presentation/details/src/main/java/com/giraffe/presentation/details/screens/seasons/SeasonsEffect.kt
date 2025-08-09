package com.giraffe.presentation.details.screens.seasons

sealed interface SeasonsEffect {
    data class Error(val error: Throwable) : SeasonsEffect
}