package com.giraffe.person.entity

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
    val movieCredits: List<PersonCredit> = emptyList(),
    val tvCredits: List<PersonCredit> = emptyList(),
)

data class PersonCredit(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double
)

enum class PersonType {
    CAST,
    CREW
}