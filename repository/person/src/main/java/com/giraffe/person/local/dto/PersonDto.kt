package com.giraffe.person.local.dto

import androidx.room.Entity

@Entity(
    tableName = "persons",
    primaryKeys = ["id", "movieId", "showId"]
)
data class PersonDto(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val role: String,
    val type: String,
    val movieId: Int = -1,
    val showId: Int = -1,
    val isRecent: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis()
)