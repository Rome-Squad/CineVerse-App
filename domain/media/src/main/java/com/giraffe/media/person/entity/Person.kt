package com.giraffe.media.person.entity

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
    val socialMedia: PersonSocialMediaLinks? = null
)

enum class PersonType {
    CAST,
    CREW
}