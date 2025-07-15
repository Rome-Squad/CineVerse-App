package com.giraffe.media.explore.screen

sealed class SearchScreenEffect {
    data class ShowError(val message: String) : SearchScreenEffect()
    object RefreshCompleted : SearchScreenEffect()
}