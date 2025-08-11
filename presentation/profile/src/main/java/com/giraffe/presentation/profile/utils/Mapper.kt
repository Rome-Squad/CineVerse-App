package com.giraffe.presentation.profile.utils

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.series.entity.Series
import com.giraffe.presentation.profile.model.CollectionUi
import com.giraffe.presentation.profile.model.RatedPoster
import com.giraffe.presentation.profile.model.SwipeablePoster
import com.giraffe.presentation.profile.model.UserUi
import com.giraffe.user.entity.User

fun User.toUi() = UserUi(
    name = this.displayName,
    username = this.username,
    imageUrl = this.avatarUrl.orEmpty()
)

fun Series.toRatedPoster(genres: List<Genre>) = RatedPoster(
    poster = this.toPoster(genres),
    rating = userRating.orEmpty(),
)

fun Series.toPoster(genres: List<Genre> = emptyList()) = Poster(
    id = id,
    name = name,
    genres = genres.joinToString(", ") { it.title },
    imageUri = posterUrl,
    rating = rating,
    date = releaseYear.orEmpty(),
    mediaTypeOfPoster = "series"
)

fun Movie.toRatedPoster(genres: List<Genre>) = RatedPoster(
    poster = this.toPoster(genres),
    rating = userRating.orEmpty(),
)

fun Movie.toPoster(genres: List<Genre> = emptyList()) = Poster(
    id = id,
    name = name,
    genres = genres.joinToString(", ") { it.title },
    imageUri = posterUrl.orEmpty(),
    rating = rating,
    date = releaseYear.orEmpty(),
    mediaTypeOfPoster = "movie"
)

fun Collection.toUi() = CollectionUi(
    id = id,
    name = name,
    itemCount = itemsCount,
    description = description
)

fun CollectionUi.toEntity() = Collection(
    id = id,
    name = name,
    itemsCount = itemCount,
    description = description
)

fun Movie.toSwipeablePoster(
    isSwiped: Boolean = false,
    genres: List<Genre> = emptyList()
) = SwipeablePoster(
    poster = toPoster(genres),
    isSwiped = isSwiped
)