package com.giraffe.media.collections.retrofit

import com.giraffe.media.collections.datasource.remote.CollectionsRemoteDataSource
import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionItemDto
import com.giraffe.media.collections.response.CollectionItemIdRequestBody
import com.giraffe.media.util.safeCallRemote
import javax.inject.Inject

class CollectionsRemoteDataSourceImpl @Inject constructor(
    private val collectionsApiService: CollectionsApiService,
) : CollectionsRemoteDataSource {
    override suspend fun getCollections(
        accountId: Int
    ): List<CollectionDto> =
        safeCallRemote { collectionsApiService.getCollections(accountId = accountId) }.results

    override suspend fun getCollectionDetails(collectionId: Int): CollectionDto =
        safeCallRemote { collectionsApiService.getCollectionDetails(collectionId = collectionId) }

    override suspend fun addCollection(collection: CollectionDto): Int? = safeCallRemote {
        collectionsApiService.addCollection(collection)
    }.collectionId

    override suspend fun removeCollection(collectionId: Int): Boolean = safeCallRemote {
        collectionsApiService.removeCollection(collectionId)
    }.isSuccess

    override suspend fun clearCollection(collectionId: Int): Boolean = safeCallRemote {
        collectionsApiService.clearCollection(collectionId)
    }.isSuccess

    override suspend fun addMovieToCollection(collectionId: Int, movieId: Int): Boolean =
        safeCallRemote {
            collectionsApiService.addMovieToCollection(
                collectionId,
                CollectionItemIdRequestBody(movieId)
            )
        }.isSuccess

    override suspend fun removeMovieFromCollection(collectionId: Int, movieId: Int): Boolean =
        safeCallRemote {
            collectionsApiService.removeMovieFromCollection(
                collectionId,
                CollectionItemIdRequestBody(movieId)
            )
        }.isSuccess

    override suspend fun getCollectionMovies(collectionId: Int): List<CollectionItemDto> =
        safeCallRemote { collectionsApiService.getCollectionItems(collectionId) }.items
}