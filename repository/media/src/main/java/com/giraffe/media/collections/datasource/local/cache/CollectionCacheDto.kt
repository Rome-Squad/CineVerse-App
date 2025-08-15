package com.giraffe.media.collections.datasource.local.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giraffe.media.utils.DatabaseConstants

@Entity(tableName = DatabaseConstants.COLLECTIONS_TABLE)
data class CollectionCacheDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String,
    val description: String,
    val itemsCount: Int = 0,
    val type: String
)