package com.giraffe.media.collections

import com.giraffe.media.collections.datasource.remote.CollectionsRemoteDataSource
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.mapper.toDto
import com.giraffe.media.collections.mapper.toEntity
import com.giraffe.media.collections.mapper.toMovie
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.utils.safeCall
import javax.inject.Inject

class CollectionsRepositoryImp @Inject constructor(
    private val collectionsRemoteDataSource: CollectionsRemoteDataSource
) : CollectionsRepository {

    override suspend fun getCollections(
        accountId: Int
    ): List<Collection> = safeCall {
        collectionsRemoteDataSource.getCollections(accountId).map { it.toEntity() }
    }


    override suspend fun getCollectionDetails(
        collectionId: Int
    ): Collection = safeCall {
        collectionsRemoteDataSource.getCollectionDetails(collectionId).toEntity()
    }

    override suspend fun addCollection(
        collection: Collection
    ) = safeCall {
        collectionsRemoteDataSource.addCollection(collection.toDto())
    }

    override suspend fun removeCollection(
        collectionId: Int
    ) = safeCall {
        collectionsRemoteDataSource.removeCollection(collectionId)
    }

    override suspend fun clearCollection(
        collectionId: Int
    ) = safeCall {
        collectionsRemoteDataSource.clearCollection(collectionId)
    }

    override suspend fun addMovieToCollection(
        collectionId: Int,
        movieId: Int
    ) = safeCall {
        collectionsRemoteDataSource.addMovieToCollection(
            collectionId,
            movieId
        )
    }

    override suspend fun removeMovieFromCollection(
        collectionId: Int,
        movieId: Int
    ) = safeCall {
        collectionsRemoteDataSource.removeMovieFromCollection(
            collectionId,
            movieId
        )
    }

    override suspend fun getCollectionMovies(
        collectionId: Int
    ): List<Movie> = safeCall {
        collectionsRemoteDataSource.getCollectionMovies(collectionId).map { it.toMovie() }
    }

}