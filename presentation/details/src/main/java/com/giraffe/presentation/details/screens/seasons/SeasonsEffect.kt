package com.giraffe.presentation.details.screens.seasons

sealed interface SeasonsEffect {
    data class ShowError(val error: Throwable) : SeasonsEffect
    object NavigateBack : SeasonsEffect
}