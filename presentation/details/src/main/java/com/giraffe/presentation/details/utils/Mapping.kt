package com.giraffe.presentation.details.utils

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.person.entity.PersonCredit

fun PersonCredit.toPoster() = Poster(
    id = id,
    name = title,
    imageUrl = posterPath.toString(),
    rating = voteAverage.toFloat(),
    date = releaseYear,
    mediaTypeOfPoster = mediaType
)