package com.giraffe.home.utils

import com.giraffe.home.screen.home.FeaturedCollectionUiModel
import com.giraffe.home.screen.home.HomeUiModel
import com.giraffe.home.screen.home.MediaType
import com.giraffe.home.screen.home.PopularMediaUiModel
import com.giraffe.home.screen.home.YourCollectionUiModel
import com.giraffe.home.screen.movies_list.PosterUiState
import com.giraffe.media.entity.Genre
import com.giraffe.media.home.entity.YourCollection
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series


fun Movie.toHomeUiModel(): HomeUiModel {
    return HomeUiModel(
        id = id,
        title = title,
        posterUrl = posterUrl ?: "",
        rating = rating,
        mediaType = MediaType.MOVIE
    )
}

fun Series.toHomeUiModel(): HomeUiModel {
    return HomeUiModel(
        id = id,
        title = name,
        posterUrl = posterUrl,
        rating = rating,
        mediaType = MediaType.SERIES
    )
}

fun Series.toPosterUi(): PosterUiState {
    return PosterUiState(
        id = id,
        name = name,
        imageUri = posterUrl,
        rating = rating,
        date = releaseYear,
        mediaType = MediaType.SERIES
    )
}

fun Movie.toPosterUi(): PosterUiState {
    return PosterUiState(
        id = id,
        name = title,
        imageUri = posterUrl ?: "",
        rating = rating,
        date = releaseYear.toString(),
        mediaType = MediaType.MOVIE
    )
}


fun Movie.toPopularMediaUiModel(genres: List<String>): PopularMediaUiModel {
    return PopularMediaUiModel(
        id = id,
        title = title,
        posterUrl = posterUrl ?: "",
        genres = genres,
        rating = rating,
        mediaType = MediaType.MOVIE
    )
}

fun Series.toPopularMediaUiModel(genres: List<String>): PopularMediaUiModel {
    return PopularMediaUiModel(
        id = id,
        title = name,
        posterUrl = posterUrl,
        genres = genres,
        rating = rating,
        mediaType = MediaType.SERIES
    )
}


fun YourCollection.toUiModel(): YourCollectionUiModel {
    return YourCollectionUiModel(
        id = id,
        title = title,
        numberOfItems = numberOfShows.toIntOrNull() ?: 0
    )
}

fun Genre.toUiModel(): FeaturedCollectionUiModel {
    return FeaturedCollectionUiModel(
        id = id,
        title = title,
        backgroundImageUrl = GenreBackgroundImage.getImageUrlForGenre(title)
    )
}

enum class GenreBackgroundImage(
    val imageUrl: String,
    val genreType: List<String>
) {
    ACTION(
        imageUrl = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=500&h=300&fit=crop",
        genreType = listOf("action", "action & adventure")
    ),
    ANIMATION(
        imageUrl = "https://images.unsplash.com/photo-1439436556258-1f7fab1bfd4f?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        genreType = listOf("animation")
    ),
    COMEDY(
        imageUrl = "https://images.unsplash.com/photo-1618861297248-3438b3d9aae9?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        genreType = listOf("comedy")
    ),
    CRIME(
        imageUrl = "https://images.unsplash.com/photo-1551884170-09fb70a3a2ed?w=500&h=300&fit=crop",
        genreType = listOf("crime")
    ),
    DOCUMENTARY(
        imageUrl = "https://images.unsplash.com/photo-1485846234645-a62644f84728?w=500&h=300&fit=crop",
        genreType = listOf("documentary")
    ),
    DRAMA(
        imageUrl = "https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=500&h=300&fit=crop",
        genreType = listOf("drama")
    ),
    FAMILY(
        imageUrl = "https://images.unsplash.com/photo-1511895426328-dc8714191300?w=500&h=300&fit=crop",
        genreType = listOf("family")
    ),
    KIDS(
        imageUrl = "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=500&h=300&fit=crop",
        genreType = listOf("kids")
    ),
    MYSTERY(
        imageUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=500&h=300&fit=crop",
        genreType = listOf("mystery")
    ),
    NEWS(
        imageUrl = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=500&h=300&fit=crop",
        genreType = listOf("news")
    ),
    REALITY(
        imageUrl = "https://images.unsplash.com/photo-1560472354-b33ff0c44a43?w=500&h=300&fit=crop",
        genreType = listOf("reality")
    ),
    SCI_FI_FANTASY(
        imageUrl = "https://images.unsplash.com/photo-1446776877081-d282a0f896e2?w=500&h=300&fit=crop",
        genreType = listOf("sci-fi & fantasy", "science fiction", "fantasy")
    ),
    SOAP(
        imageUrl = "https://images.unsplash.com/photo-1598300042247-d088f8ab3a91?w=500&h=300&fit=crop",
        genreType = listOf("soap")
    ),
    TALK(
        imageUrl = "https://images.unsplash.com/photo-1478737270239-2f02b77fc618?w=500&h=300&fit=crop",
        genreType = listOf("talk")
    ),
    WAR_POLITICS(
        imageUrl = "https://images.unsplash.com/photo-1494972688394-4cc796f9e4c5?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        genreType = listOf("war & politics", "war", "politics")
    ),
    WESTERN(
        imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=500&h=300&fit=crop",
        genreType = listOf("western")
    );

    companion object {
        private const val DEFAULT_IMAGE_URL =
            "https://drive.google.com/uc?export=download&id=16psefCb52QbPtMbCCNbAtOocHq6dr3ol"

        fun getImageUrlForGenre(genreName: String): String {
            val normalizedGenre = genreName.lowercase().trim()

            return entries.find { genre ->
                genre.genreType.any { alias ->
                    alias.lowercase() == normalizedGenre
                }
            }?.imageUrl ?: DEFAULT_IMAGE_URL
        }
    }
}