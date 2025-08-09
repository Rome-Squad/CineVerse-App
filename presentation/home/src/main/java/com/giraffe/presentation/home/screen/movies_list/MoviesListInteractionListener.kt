package com.giraffe.presentation.home.screen.movies_list

import com.giraffe.presentation.home.screen.home.MediaType

interface MoviesListInteractionListener {
    fun onViewChanged(isGrid: Boolean)
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
}