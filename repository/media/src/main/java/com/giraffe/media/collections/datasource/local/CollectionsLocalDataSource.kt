package com.giraffe.media.collections.datasource.local

import com.giraffe.media.collections.datasource.local.cache.CollectionCacheDto

interface CollectionsLocalDataSource {

    suspend fun getCollections(): List<CollectionCacheDto>

    suspend fun insertCollection(collection: CollectionCacheDto)

    suspend fun insertCollections(collections: List<CollectionCacheDto>)

    suspend fun deleteCollection(collectionId: Int): Boolean

    suspend fun increaseCollectionItemCount(collectionId: Int)

    suspend fun decreaseCollectionItemCount(collectionId: Int)

    suspend fun clearCollectionsCache()
}