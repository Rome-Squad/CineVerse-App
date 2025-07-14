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
    val descriptionInfoSection: String = "Christian Charles Philip Bale (born 30 January 1974) is an English actor. Known for his versatility and physical transformations for his roles, he has been a leading man in films of several genres. He has received various accolades, including an Academy Award and two Golden Globe Awards. Forbes magazine ranked him as one of the highest-paid actors in 2014.\n" +
            "Born in Wales to English parents, Bale had his breakthrough role at age 13 in Steven Spielberg's 1987 war film Empire of the Sun. After more than a decade of performing in leading and supporting roles in films, he gained wider recognition for his portrayals of serial killer Patrick Bateman in the black comedy American Psycho (2000) and the titular role in the psychological thriller The Machinist (2004). In 2005, he played superhero Batman in Batman Begins and again in The Dark Knight (2008) and The Dark Knight Rises (2012), garnering acclaim for his performance in the trilogy, which is one of the highest-grossing film franchises.",
    val actorGalleryImages: List<String?> = listOf(
        null,
        null,
        "https://m.media-amazon.com/images/M/MV5BZDU4MGExZGEtMWRlMC00NjRhLThhZGQtMGIxMDFlNjE5MWVlXkEyXkFqcGc@._V1_QL75_UX169_.jpg"
    ),
    val actorGalleryImagesDescriptions: List<String?> = listOf(
        "gallery_image_one",
        "gallery_image_two",
        "gallery_image_three"
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