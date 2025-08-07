package com.giraffe.profile.screens.history

interface HistoryInteractionListener {
    fun onDeleteClicked(id:Int)
    fun onCloseClicked()
    fun onMediaClicked(mediaId: Int,mediaType: String)
    fun navigateToExploreScreen()

}