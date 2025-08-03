package com.giraffe.media.collections

import com.giraffe.media.collections.datasource.remote.CollectionsRemoteDataSource
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.mapper.toDto
import com.giraffe.media.collections.mapper.toEntity
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.mapper.toEntity

class CollectionsRepositoryImp(
    private val collectionsRemoteDataSource: CollectionsRemoteDataSource
) : CollectionsRepository {
    override suspend fun getCollections(): List<Collection> =
        collectionsRemoteDataSource.getCollections().map { it.toEntity() }

    override suspend fun getCollectionDetails(
        collectionId: Int
    ): Collection = collectionsRemoteDataSource.getCollectionDetails(collectionId).toEntity()

    override suspend fun addCollection(
        collection: Collection
    ) = collectionsRemoteDataSource.addCollection(collection.toDto())

    override suspend fun removeCollection(
        collectionId: Int
    ) = collectionsRemoteDataSource.removeCollection(collectionId)

    override suspend fun clearCollection(
        collectionId: Int
    ) = collectionsRemoteDataSource.clearCollection(collectionId)

    override suspend fun addMovieToCollection(
        collectionId: Int,
        movieId: Int
    ) = collectionsRemoteDataSource.addMovieToCollection(
        collectionId,
        movieId
    )

    override suspend fun removeMovieFromCollection(
        collectionId: Int,
        movieId: Int
    ) = collectionsRemoteDataSource.removeMovieFromCollection(
        collectionId,
        movieId
    )

    override suspend fun getCollectionMovies(
        collectionId: Int
    ): List<Movie> =
        collectionsRemoteDataSource.getCollectionMovies(collectionId).map { it.toEntity() }

    override suspend fun addSeriesToCollection(
        collectionId: Int,
        seriesId: Int
    ) = collectionsRemoteDataSource.addSeriesToCollection(
        collectionId,
        seriesId
    )

    override suspend fun removeSeriesFromCollection(
        collectionId: Int,
        seriesId: Int
    ) = collectionsRemoteDataSource.removeSeriesFromCollection(
        collectionId,
        seriesId
    )

    override suspend fun getCollectionSeries(
        collectionId: Int
    ): List<Series> =
        collectionsRemoteDataSource.getCollectionSeries(collectionId).map { it.toEntity() }

}