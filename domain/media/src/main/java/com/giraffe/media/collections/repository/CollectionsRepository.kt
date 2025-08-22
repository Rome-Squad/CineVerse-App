package com.giraffe.media.collections.repository

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.movie.entity.Movie
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {

    fun getCollections(accountId: Int): Flow<List<Collection>>

    suspend fun getCollectionsByMovieId(movieId: Int): List<Collection>

    suspend fun getCollectionDetails(collectionId: Int): Collection

    suspend fun addCollection(collection: Collection): Int?

    suspend fun removeCollection(collectionId: Int): Boolean

    suspend fun clearCollection(collectionId: Int): Boolean


    suspend fun addMovieToCollection(collectionId: Int, movieId: Int): Boolean

    suspend fun removeMovieFromCollection(collectionId: Int, movieId: Int): Boolean

    suspend fun getCollectionMovies(collectionId: Int): List<Movie>

    suspend fun clearCollectionsCache()
}
