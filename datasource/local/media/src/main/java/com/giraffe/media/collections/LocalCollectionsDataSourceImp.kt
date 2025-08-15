package com.giraffe.media.collections

import com.giraffe.media.collections.dao.CollectionsDao
import com.giraffe.media.collections.datasource.local.CollectionsLocalDataSource
import com.giraffe.media.collections.datasource.local.cache.CollectionCacheDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalCollectionsDataSourceImp @Inject constructor(
    private val collectionsDao: CollectionsDao
): CollectionsLocalDataSource {
    override fun getCollections(): Flow<List<CollectionCacheDto>> = collectionsDao.getCollections()

    override suspend fun insertCollection(
        collection: CollectionCacheDto
    ) = collectionsDao.insertCollection(collection)

    override suspend fun insertCollections(
        collections: List<CollectionCacheDto>
    ) = collectionsDao.insertCollections(collections)

    override suspend fun deleteCollection(
        collectionId: Int
    ): Boolean = collectionsDao.deleteCollection(collectionId)

    override suspend fun increaseCollectionItemCount(
        collectionId: Int
    ) = collectionsDao.increaseCollectionItemCount(collectionId)

    override suspend fun decreaseCollectionItemCount(
        collectionId: Int
    ) = collectionsDao.decreaseCollectionItemCount(collectionId)

    override suspend fun clearCollectionsCache() = collectionsDao.clearCollectionsCache()
}