package com.giraffe.media.collections.datasource.remote

import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionItemDto

interface CollectionsRemoteDataSource {


    suspend fun getCollections(accountId: Int): List<CollectionDto>

    suspend fun getCollectionsByMovieId(movieId: Int): List<CollectionDto>

    suspend fun getCollectionDetails(collectionId: Int): CollectionDto

    suspend fun addCollection(collection: CollectionDto): Int?

    suspend fun removeCollection(collectionId: Int): Boolean

    suspend fun clearCollection(collectionId: Int): Boolean


    suspend fun addMovieToCollection(collectionId: Int, movieId: Int): Boolean

    suspend fun removeMovieFromCollection(collectionId: Int, movieId: Int): Boolean

    suspend fun getCollectionMovies(collectionId: Int): List<CollectionItemDto>

}