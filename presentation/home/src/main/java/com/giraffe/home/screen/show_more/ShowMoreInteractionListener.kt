package com.giraffe.home.screen.show_more

import com.giraffe.home.screen.home.MediaType

interface ShowMoreInteractionListener {
    fun onViewChanged(isGrid: Boolean)
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
}