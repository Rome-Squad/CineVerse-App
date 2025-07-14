package com.giraffe.person.entity

data class PersonDetails(
    val personInfo: PersonInfo,
    val images: List<String>,
    val movieCredits: List<PersonCredit>,
    val tvCredits: List<PersonCredit>
)
data class PersonInfo(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String?,
    val placeOfBirth: String?,
    val imageUrl: String?
)

data class PersonCredit(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double
)