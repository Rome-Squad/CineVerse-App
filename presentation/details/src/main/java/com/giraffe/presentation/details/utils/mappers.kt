package com.giraffe.presentation.details.utils

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.entity.PersonSocialMediaLinks
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.model.CastUi
import com.giraffe.presentation.details.model.CollectionUi
import com.giraffe.presentation.details.model.CrewUi
import com.giraffe.presentation.details.model.MovieUi
import com.giraffe.presentation.details.model.ReviewUI
import com.giraffe.presentation.details.model.SeasonUi
import com.giraffe.presentation.details.model.SeriesUi
import com.giraffe.presentation.details.model.SocialMediaPlatform
import com.giraffe.presentation.details.model.SocialMediaUi

fun PersonCredit.toPoster() = Poster(
    id = id,
    name = title,
    imageUri = posterPath.toString(),
    rating = voteAverage.toFloat(),
    date = releaseYear,
    mediaTypeOfPoster = mediaType
)


fun Person.toCastUi(): CastUi {
    return CastUi(
        id = id,
        name = name,
        role = role,
        urlImage = imageUrl
    )
}


fun Collection.toUi() = CollectionUi(
    id = id,
    title = name,
    isLoading = false
)


fun Person.toCrewUi(): CrewUi {
    return CrewUi(
        name = name,
        role = role
    )
}

fun List<CrewUi>.groupByRole(): Map<String, List<String>> {
    return this.groupBy { it.role }.mapValues { it.value.map { member -> member.name } }
}


fun Movie.toMovieUi(genres: List<Genre> = emptyList()) = MovieUi(
    id = id,
    title = title,
    description = description,
    rating = rating,
    duration = if (duration != null && duration!! > 0) duration?.toFormattedDuration() else null,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genresID = genresID,
    genres = genres.map { it.title },
    releaseYear = if (releaseYear != null) releaseYear.toString().toFormattedDate() else null,
    youtubeVideoId = youtubeVideoId.orEmpty()

)

fun MovieUi.toPoster(): Poster {
    return Poster(
        id = id,
        name = title,
        imageUri = posterUrl.orEmpty(),
        rating = rating,
        genres = if (genres.isNotEmpty()) genres.joinToString(", ") else null,
        time = duration,
        date = releaseYear
    )
}


fun ReviewUI.toReviewEntity(): Review {
    return Review(
        id = id,
        authorImageUrl = authorImageUrl,
        authorName = authorName,
        authorUserName = authorUserName,
        content = content,
        rating = rating,
        createdAt = createdAt
    )
}

fun Review.toReviewUI(): ReviewUI {
    return ReviewUI(
        id = id,
        authorImageUrl = authorImageUrl,
        authorName = authorName,
        authorUserName = authorUserName,
        content = content,
        rating = rating,
        createdAt = createdAt
    )
}

fun Season.toSeasonUi() = SeasonUi(
    id = id,
    overview = overview,
    rating = rating,
    posterUrl = posterUrl,
    releaseYear = releaseYear,
    seasonNumber = seasonNumber,
    episodeCount = episodeCount
)


fun Series.toSeriesUi(
    genres: List<Genre> = emptyList()
) = SeriesUi(
    id = id,
    name = name,
    overview = overview,
    rating = rating,
    posterUrl = posterUrl,
    releaseYear = releaseYear,
    genres = genres.map { it.title },
    youtubeVideoId = youtubeVideoId
)

fun SeriesUi.toPoster(): Poster = Poster(
    id = id,
    name = name,
    imageUri = posterUrl.orEmpty(),
    rating = rating,
    genres = genres.joinToString(", "),
    date = releaseYear
)


fun PersonSocialMediaLinks.toUiState(): List<SocialMediaUi> {
    val socialMediaUiList: MutableList<SocialMediaUi> = mutableListOf()
    youtubeLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.YOUTUBE,
                url = url,
                name = R.string.youtube,
                contentDescription = R.string.youtube_icon
            )
        )
    }

    facebookLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.FACEBOOK,
                url = url,
                name = R.string.facebook,
                contentDescription = R.string.facebook_icon
            )
        )
    }

    instagramLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.INSTAGRAM,
                url = url,
                name = R.string.instagram,
                contentDescription = R.string.instagram_icon
            )
        )
    }

    twitterLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.TWITTER,
                url = url,
                name = R.string.twitter,
                contentDescription = R.string.twitter_icon
            )
        )
    }

    tiktokLink?.let { url ->
        socialMediaUiList.add(
            SocialMediaUi(
                platform = SocialMediaPlatform.TIKTOK,
                url = url,
                name = R.string.tiktok,
                contentDescription = R.string.tiktok_icon
            )
        )
    }
    return socialMediaUiList
}

