package com.giraffe.presentation.profile.utils

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.series.entity.Series
import com.giraffe.presentation.profile.model.CollectionUi
import com.giraffe.presentation.profile.model.RatedPoster
import com.giraffe.presentation.profile.model.SwipeablePoster
import com.giraffe.presentation.profile.model.UserUi
import com.giraffe.presentation.profile.uimodel.Poster
import com.giraffe.user.entity.User

fun User.toUi() = UserUi(
    name = this.displayName,
    username = this.username,
    imageUrl = this.avatarUrl.orEmpty()
)

fun Series.toRatedPoster(genres: List<Genre>) = RatedPoster(
    poster = this.toPoster(genres),
    rating = userRating.orZero(),
)

fun Series.toPoster(genres: List<Genre> = emptyList()) = Poster(
    id = id,
    name = name,
    genres = genres.filter { it.id in genreIDs }.joinToString(", ") { it.title },
    imageUrl = posterUrl,
    rating = rating,
    date = releaseYear.toFormattedDate(),
    mediaTypeOfPoster = "series",
    recentViewedAt = recentViewedAt ?: 0L
)

fun Movie.toRatedPoster(genres: List<Genre>) = RatedPoster(
    poster = this.toPoster(genres),
    rating = userRating.orZero(),
)

fun Movie.toPoster(genres: List<Genre> = emptyList()) = Poster(
    id = id,
    name = name,
    genres = genres.filter { it.id in this.genresID }.joinToString(", ") { it.title },
    imageUrl = posterUrl,
    rating = rating,
    date = releaseYear.toFormattedDate(),
    mediaTypeOfPoster = "movie",
    recentViewedAt = recentViewedAt ?: 0L
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