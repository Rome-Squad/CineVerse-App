package com.giraffe.presentation.details.model


data class SocialMediaUi(
    val platform: SocialMediaPlatform,
    val url: String,
    val name: Int,
    val contentDescription: Int
)

enum class SocialMediaPlatform {
    YOUTUBE, FACEBOOK, INSTAGRAM, TWITTER, TIKTOK
}
