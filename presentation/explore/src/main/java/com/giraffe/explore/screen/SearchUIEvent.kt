package com.giraffe.explore.screen

sealed class SearchUIEvent {
    data class ShowError(val message: String) : SearchUIEvent()
    object ShowLoading : SearchUIEvent()
    object HideLoading : SearchUIEvent()
    object RefreshCompleted : SearchUIEvent()
}