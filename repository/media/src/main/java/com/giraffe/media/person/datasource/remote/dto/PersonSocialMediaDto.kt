package com.giraffe.media.person.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonSocialMediaDto(
    @SerialName("facebook_id")
    val facebookId: String? = null,
    @SerialName("instagram_id")
    val instagramId: String? = null,
    @SerialName("youtube_id")
    val youtubeId: String? = null,
    @SerialName("tiktok_id")
    val tiktokId: String? = null,
    @SerialName("twitter_id")
    val twitterId: String? = null,
)