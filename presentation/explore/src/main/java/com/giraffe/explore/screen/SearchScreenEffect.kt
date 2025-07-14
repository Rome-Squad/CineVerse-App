package com.giraffe.explore.screen

sealed class SearchScreenEffect {
    data class ShowError(val message: String) : SearchScreenEffect()
    object ShowLoading : SearchScreenEffect()
    object HideLoading : SearchScreenEffect()
    object RefreshCompleted : SearchScreenEffect()
}