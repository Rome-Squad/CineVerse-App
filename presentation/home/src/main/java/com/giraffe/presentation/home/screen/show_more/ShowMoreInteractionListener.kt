package com.giraffe.presentation.home.screen.show_more



import com.giraffe.presentation.home.model.MediaType

interface ShowMoreInteractionListener {
    fun onViewChanged(isGrid: Boolean)
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
}