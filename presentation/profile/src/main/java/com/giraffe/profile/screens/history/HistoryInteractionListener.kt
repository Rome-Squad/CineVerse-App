package com.giraffe.profile.screens.history

interface HistoryInteractionListener {
    fun onDeleteClicked()
    fun onCloseClicked()
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
    fun navigateToExploreScreen(id:Int)

}