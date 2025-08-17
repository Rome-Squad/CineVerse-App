package com.giraffe.presentation.home.screen.show_more


import com.giraffe.presentation.home.model.MediaType

interface CategoryMediaInteractionListener {

    fun onViewChanged(isGrid: Boolean)

    fun onMediaClicked(mediaId: Int, mediaType: MediaType)

    fun onBackClick()

    fun onRetryClick()
}