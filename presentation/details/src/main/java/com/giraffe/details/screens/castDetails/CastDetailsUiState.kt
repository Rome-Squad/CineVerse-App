package com.giraffe.details.screens.castDetails

import com.giraffe.designsystem.uimodel.Poster

data class CastDetailsUiState(
    val actorImage: String = "",
    val actorName: String = "",
    val actorBirth: String = "",
    val actorPlace: String = "",
    val actorYouTubeLink: String = "",
    val actorFacebookLink: String = "",
    val actorInstagramLink: String = "",
    val titleMoviesSection: String = "Best of Christian Bale",
    val titleInfoSection: String = "Biography",
    val descriptionInfoSection: String = "English actor. Known for his versatility and physical transformations for his roles",
    val actorGalleryImages: List<Pair<String?, String?>> = listOf(
        Pair(null, "gallery_image_one"),
        Pair(null, "gallery_image_two"),
        Pair(
            "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            "gallery_image_three"
        ),
    ),
    val posters: List<Poster> = listOf(
        Poster(
            id = 1,
            name = "The Flash",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 7.5f,
        ),
        Poster(
            id = 2,
            name = "The Flash",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 7.5f,
        ),
        Poster(
            id = 3,
            name = "The Flash",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 7.5f,
        ),
        Poster(
            id = 4,
            name = "The Flash",
            imageUri = "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg",
            rating = 7.5f,
        ),
    ),
)