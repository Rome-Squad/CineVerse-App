package com.giraffe.person.entity

data class Person(
    val id: Int,
    val name: String,
    val role: String,
    val imageUrl: String? = null,
    val movieId: Int = -1,
    val showId: Int = -1,
    val type: PersonType = PersonType.CAST
)

enum class PersonType {
    CAST,
    CREW
}