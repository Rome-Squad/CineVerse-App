package com.giraffe.media.mediaMember.util

import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.entity.CrewMember
import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks

fun createCrewMember(
    id: Int,
    name: String,
    role: String,
    biography: String? = null,
    birthday: String? = null,
    imageUrl: String? = null,
    otherImages: List<String>? = null,
    placeOfBirth: String? = null,
    socialMedia: SocialMediaLinks? = null
) = CrewMember(
    id = id,
    name = name,
    role = role,
    biography = biography,
    birthday = birthday,
    imageUrl = imageUrl,
    otherImages = otherImages,
    placeOfBirth = placeOfBirth,
    socialMedia = socialMedia,
)

fun createCastMember(
    id: Int,
    name: String,
    role: String = "Acting",
    biography: String? = null,
    birthday: String? = null,
    characterName: String? = null,
    imageUrl: String? = null,
    otherImages: List<String>? = null,
    placeOfBirth: String? = null,
    socialMedia: SocialMediaLinks? = null
) = CastMember(
    id = id,
    name = name,
    role = role,
    biography = biography,
    birthday = birthday,
    imageUrl = imageUrl,
    otherImages = otherImages,
    placeOfBirth = placeOfBirth,
    socialMedia = socialMedia,
    characterName = characterName
)