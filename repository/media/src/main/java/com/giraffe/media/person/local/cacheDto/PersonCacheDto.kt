package com.giraffe.media.person.local.cacheDto

import androidx.room.Entity

@Entity(
    tableName = "persons",
    primaryKeys = ["id", "movieId", "seriesId"]
)
data class PersonCacheDto(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val role: String,
    val type: String,
    val movieId: Int = -1,
    val seriesId: Int = -1,
    val isRecent: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis()
)