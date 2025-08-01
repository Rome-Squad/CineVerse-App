package com.giraffe.details.screens.castDetails

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.person.entity.PersonSocialMediaLinks

data class CastDetailsUiState(
    val actorId: Int = 0,
    val actorImageUrl: String = "",
    val actorName: String = "",
    val actorBirth: String = "",
    val actorPlace: String = "",
    val biographyInfo: String = "",
    val actorGalleryImageUrls: List<String?> = emptyList(),
    val posters: List<Poster> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val socialMediaLinks: SocialMediaLinksUiState? = SocialMediaLinksUiState()
)

data class SocialMediaLinksUiState(
    val facebookLink: String? = null,
    val instagramLink: String? = null,
    val youtubeLink: String? = null,
    val tiktokLink: String? = null,
    val twitterLink: String? = null,
)

fun PersonSocialMediaLinks.toUiState() = SocialMediaLinksUiState(
    facebookLink = facebookLink,
    instagramLink = instagramLink,
    youtubeLink = youtubeLink,
    tiktokLink = tiktokLink,
    twitterLink = twitterLink,
)

