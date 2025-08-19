package com.giraffe.media.mediaMember.entity

import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks
import kotlinx.datetime.LocalDate

data class CastMember(
    val id: Int,
    val name: String,
    val role: String,
    val otherImages: List<String>,
    val imageUrl: String?,
    val biography: String?,
    val birthday: LocalDate?,
    val characterName: String?,
    val placeOfBirth: String?,
    val socialMedia: SocialMediaLinks?
)