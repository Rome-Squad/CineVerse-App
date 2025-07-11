package com.giraffe.person.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class PersonDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val role: String,
    val character: String? = null,
    val isRecent: Boolean = false
)