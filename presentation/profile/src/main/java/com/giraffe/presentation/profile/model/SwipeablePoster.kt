package com.giraffe.presentation.profile.model

import com.giraffe.presentation.profile.uimodel.Poster

data class SwipeablePoster(
    val poster: Poster,
    val isSwiped: Boolean
)