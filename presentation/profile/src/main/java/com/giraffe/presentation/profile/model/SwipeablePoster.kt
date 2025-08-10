package com.giraffe.presentation.profile.model

import com.giraffe.designsystem.uimodel.Poster

data class SwipeablePoster(
    val poster: Poster,
    val isSwiped: Boolean
)