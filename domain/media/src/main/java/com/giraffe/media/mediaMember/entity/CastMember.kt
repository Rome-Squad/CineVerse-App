package com.giraffe.media.mediaMember.entity

import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks

data class CastMember(
    val id: Int,
    val name: String,
    val role: String,
    val imageUrl: String?,
    val biography: String?,
    val birthday: String?,
    val characterName: String?,
    val otherImages: List<String>?,
    val placeOfBirth: String?,
    val socialMedia: SocialMediaLinks?
)