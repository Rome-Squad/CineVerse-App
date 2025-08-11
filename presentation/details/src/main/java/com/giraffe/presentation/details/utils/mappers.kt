package com.giraffe.presentation.details.utils

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.entity.CrewMember
import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks
import com.giraffe.media.movie.entity.Movie
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

fun Movie.toUi(genres: List<Genre> = emptyList()) = MovieUi(
    id = id,
    title = title,
    description = description,
    rating = rating,
    duration = duration?.toFormattedDuration(),
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    genresID = genresID,
    genres = genres.map { it.title },
    releaseYear = if (releaseYear != null) releaseYear.toString().toFormattedDate() else null,
    youtubeVideoId = youtubeVideoId.orEmpty(),
)

fun MovieUi.toPoster() = Poster(
    id = id,
    name = title,
    imageUrl = posterUrl.orEmpty(),
    rating = rating,
    genres = if (genres.isNotEmpty()) genres.joinToString(", ") else null,
    time = duration,
    date = releaseYear
)

fun ReviewUI.toEntity() = Review(
    id = id,
    authorImageUrl = authorImageUrl,
    authorName = authorName,
    authorUserName = authorUserName,
    content = content,
    rating = rating,
    createdAt = createdAt
)

fun Collection.toUi() = CollectionUi(
    id = id,
    title = name,
    isLoading = false
)

fun Review.toUi() = ReviewUI(
    id = id,
    authorImageUrl = authorImageUrl,
    authorName = authorName,
    authorUserName = authorUserName,
    content = content,
    rating = rating,
    createdAt = createdAt
)


fun Season.toUi() = SeasonUi(
    id = id,
    overview = overview,
    rating = rating,
    posterUrl = posterUrl,
    releaseYear = releaseYear?.toString()?.toFormattedDate().orEmpty(),
    seasonNumber = seasonNumber,
    episodeCount = episodeCount
)


fun Series.toUi(
    genres: List<Genre> = emptyList()
) = SeriesUi(
    id = id,
    name = name,
    overview = overview,
    rating = rating,
    posterUrl = posterUrl,
    releaseYear = releaseYear?.toString()?.toFormattedDate().orEmpty(),
    genres = genres.map { it.title },
    youtubeVideoId = youtubeVideoId.orEmpty()
)

fun SeriesUi.toPoster(): Poster = Poster(
    id = id,
    name = name,
    imageUrl = posterUrl.orEmpty(),
    rating = rating,
    genres = genres.joinToString(", "),
    date = releaseYear
)


fun SocialMediaLinks.toSocialMediaUi(): List<SocialMediaUi> {
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

fun CastMember.toCastUi() = CastUi(
    id = id,
    name = name,
    role = role,
    urlImage = imageUrl
)

fun CrewMember.toCrewUi() = CrewUi(
    name = name,
    role = role
)


fun List<CrewUi>.groupByRole(): Map<String, List<String>> {
    return this.groupBy { it.role }.mapValues { it.value.map { member -> member.name } }
}