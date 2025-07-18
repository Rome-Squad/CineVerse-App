package com.giraffe.media.person.datasource.local.cacheDto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants.PERSONS_TABLE

@Entity(
    tableName = PERSONS_TABLE,
)
data class PersonCacheDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val role: String,
    val type: String,
    val isRecent: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis()
)