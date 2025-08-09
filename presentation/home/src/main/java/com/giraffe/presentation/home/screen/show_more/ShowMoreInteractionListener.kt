<<<<<<<< HEAD:presentation/home/src/main/java/com/giraffe/home/screen/show_more/ShowMoreInteractionListener.kt
package com.giraffe.home.screen.show_more
========
package com.giraffe.presentation.home.screen.movies_list
>>>>>>>> origin/develop:presentation/home/src/main/java/com/giraffe/presentation/home/screen/movies_list/MoviesListInteractionListener.kt

import com.giraffe.presentation.home.screen.home.MediaType

interface ShowMoreInteractionListener {
    fun onViewChanged(isGrid: Boolean)
    fun onMediaClicked(mediaId: Int, mediaType: MediaType)
}