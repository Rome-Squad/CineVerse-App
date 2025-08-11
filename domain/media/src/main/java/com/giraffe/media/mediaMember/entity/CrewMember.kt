package com.giraffe.media.mediaMember.entity

import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks

data class CrewMember(
    val id: Int,
    val name: String,
    val role: String,
    val otherImages: List<String>,
    val biography: String?,
    val birthday: String?,
    val imageUrl: String?,
    val placeOfBirth: String?,
    val socialMedia: SocialMediaLinks?
)