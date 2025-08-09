package com.giraffe.presentation.profile.screens.history

interface HistoryInteractionListener {
    fun onDeleteClicked(id: Int, mediaType: String)
    fun onCloseClicked()
    fun onMediaClicked(mediaId: Int, mediaType: String)
    fun navigateToExploreScreen()
}