package com.giraffe.home.screen.movies_list

import com.giraffe.home.screen.home.MediaType

interface MoviesListInteractionListener {
    fun onViewChanged(isGrid: Boolean)
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
}