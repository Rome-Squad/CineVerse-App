package com.giraffe.media.collections.retrofit

import com.giraffe.media.collections.datasource.remote.CollectionsRemoteDataSource
import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionItemDto
import com.giraffe.media.collections.response.CollectionItemIdRequestBody
import com.giraffe.media.util.RetrofitRequestBuilder
import javax.inject.Inject

class CollectionsRemoteDataSourceImp @Inject constructor(
    private val requestBuilder: RetrofitRequestBuilder<CollectionsApiServiceRetrofit>
) : CollectionsRemoteDataSource {
    override suspend fun getCollections(): List<CollectionDto> = requestBuilder.get {
        //temp account id until the account id is ready
        getCollections(22128475)
    }.results

    override suspend fun getCollectionDetails(collectionId: Int): CollectionDto =
        requestBuilder.get {
            getCollectionDetails(collectionId)
        }

    override suspend fun addCollection(collection: CollectionDto): Int? = requestBuilder.post {
        addCollection(collection)
    }.collectionId

    override suspend fun removeCollection(collectionId: Int): Boolean = requestBuilder.delete {
        removeCollection(collectionId)
    }.isSuccess

    override suspend fun clearCollection(collectionId: Int): Boolean = requestBuilder.post {
        clearCollection(collectionId)
    }.isSuccess

    override suspend fun addMovieToCollection(collectionId: Int, movieId: Int): Boolean =
        requestBuilder.post {
            addMovieToCollection(
                collectionId,
                CollectionItemIdRequestBody(movieId)
            )
        }.isSuccess

    override suspend fun removeMovieFromCollection(collectionId: Int, movieId: Int): Boolean =
        requestBuilder.post {
            removeMovieFromCollection(
                collectionId,
                CollectionItemIdRequestBody(movieId)
            )
        }.isSuccess

    override suspend fun getCollectionMovies(collectionId: Int): List<CollectionItemDto> =
        requestBuilder.get {
            getCollectionItems(collectionId)
        }.items
}