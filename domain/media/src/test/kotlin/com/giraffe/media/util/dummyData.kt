package com.giraffe.media.util

import com.giraffe.media.entity.Genre

val fakeTopGenre = Genre(
    id = 1, title = "Action",
    rank = 2
)

val genreWithZeroRank = Genre(id = 2, title = "Sci-Fi", rank = 0)


val fakeGenres = listOf(
    fakeTopGenre,
    genreWithZeroRank,
    Genre(
        id = 3, title = "Comedy",
        rank = 0
    )
)

const val page = 1
const val limit = 10