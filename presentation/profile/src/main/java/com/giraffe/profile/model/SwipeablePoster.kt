package com.giraffe.profile.model

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.movies.entity.Movie
import com.giraffe.profile.utils.toPosterUi

data class SwipeablePoster(
    val poster: Poster,
    val isSwiped: Boolean
)

fun Movie.toSwipeablePoster(
    isSwiped: Boolean = false
) = SwipeablePoster(
    poster = toPosterUi(),
    isSwiped = isSwiped
)