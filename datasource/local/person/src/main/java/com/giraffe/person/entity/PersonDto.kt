package com.giraffe.person.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class PersonDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val role: String,
    val character: String? = null
)

fun Person.toDto(): PersonDto {
    val role = when (this.role) {
        is Role.Actor -> "Actor"
        is Role.Director -> "Director"
        is Role.ScreenPlay -> "ScreenPlay"
        is Role.Story -> "Story"
    }
    return PersonDto(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        role = role,
        character = this.role.character
    )
}

fun PersonDto.toEntity(): Person {
    val role = when (this.role) {
        "Actor" -> Role.Actor(this.character)
        "Director" -> Role.Director
        "ScreenPlay" -> Role.ScreenPlay
        "Story" -> Role.Story
        else -> throw IllegalArgumentException("Unknown role type: ${this.role}")
    }
    return Person(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        role = role
    )
}