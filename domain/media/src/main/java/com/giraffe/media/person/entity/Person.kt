package com.giraffe.media.person.entity

import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks

data class Person(
    val id: Int,
    val name: String,
    val role: String,
    val type: PersonType = PersonType.CAST,
    val imageUrl: String? = null,
    val biography: String? = null,
    val birthday: String? = null,
    val placeOfBirth: String? = null,
    val images: List<String> = emptyList(),
    val personCredits: List<PersonCredit> = emptyList(),
    val socialMedia: SocialMediaLinks? = null
)

enum class PersonType {
    CAST,
    CREW
}