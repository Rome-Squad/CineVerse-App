package com.giraffe.media.collections.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.giraffe.media.collections.datasource.local.cache.CollectionCacheDto
import com.giraffe.media.utils.DatabaseConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionsDao {

    @Query("SELECT * FROM ${DatabaseConstants.COLLECTIONS_TABLE}")
    fun getCollections(): Flow<List<CollectionCacheDto>>

    @Insert
    suspend fun insertCollection(collection: CollectionCacheDto)

    @Insert
    suspend fun insertCollections(collections: List<CollectionCacheDto>)

    @Query("DELETE FROM ${DatabaseConstants.COLLECTIONS_TABLE} WHERE id = :collectionId")
    suspend fun deleteCollection(collectionId: Int): Boolean

    @Query(
        """
    UPDATE ${DatabaseConstants.COLLECTIONS_TABLE} 
    SET itemsCount = itemsCount + 1 
    WHERE id = :collectionId
"""
    )
    suspend fun increaseCollectionItemCount(collectionId: Int)

    @Query(
        """
    UPDATE ${DatabaseConstants.COLLECTIONS_TABLE} 
    SET itemsCount = itemsCount - 1 
    WHERE id = :collectionId
"""
    )
    suspend fun decreaseCollectionItemCount(collectionId: Int)

    @Query("DELETE FROM collections")
    suspend fun clearCollectionsCache()
}