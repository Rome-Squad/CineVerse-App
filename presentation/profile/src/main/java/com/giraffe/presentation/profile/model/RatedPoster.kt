package com.giraffe.presentation.profile.model

import com.giraffe.presentation.profile.uimodel.Poster

data class RatedPoster(
    val poster: Poster,
    val rating: Float
)