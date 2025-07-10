package com.giraffe.person.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class PersonDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val role: String,
    val character: String? = null
)