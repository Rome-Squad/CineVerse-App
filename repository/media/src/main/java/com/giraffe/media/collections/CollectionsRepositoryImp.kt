package com.giraffe.media.collections

import com.giraffe.media.collections.datasource.local.CollectionsLocalDataSource
import com.giraffe.media.collections.datasource.local.cache.CollectionCacheDto
import com.giraffe.media.collections.datasource.remote.CollectionsRemoteDataSource
import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.collections.datasource.remote.dto.CollectionItemDto
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.mapper.toCacheDto
import com.giraffe.media.collections.mapper.toDto
import com.giraffe.media.collections.mapper.toEntity
import com.giraffe.media.collections.mapper.toMovie
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.utils.safeCall
import com.giraffe.media.utils.safeFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionsRepositoryImp @Inject constructor(
    private val collectionsRemoteDataSource: CollectionsRemoteDataSource,
    private val collectionsLocalDataSource: CollectionsLocalDataSource
) : CollectionsRepository {

    override fun getCollections(
        accountId: Int
    ): Flow<List<Collection>> = safeFlow {
        collectionsLocalDataSource.getCollections()
            .map {
                it.map(CollectionCacheDto::toEntity)
                    .ifEmpty {
                        collectionsRemoteDataSource.getCollections(accountId)
                            .map(CollectionDto::toEntity)
                            .also { collections ->
                                collectionsLocalDataSource.insertCollections(
                                    collections.map(Collection::toCacheDto)
                                )
                            }
                    }
            }
    }

    override suspend fun getCollectionsByMovieId(movieId: Int): List<Collection> =
        safeCall {
            collectionsRemoteDataSource.getCollectionsByMovieId(movieId)
                .map(CollectionDto::toEntity)
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
            .also {
                it?.let {
                    collectionsLocalDataSource.insertCollection(
                        collection.copy(id = it).toCacheDto()
                    )
                }
            }
    }

    override suspend fun removeCollection(
        collectionId: Int
    ) = safeCall {
        collectionsRemoteDataSource.removeCollection(collectionId)
            .also {
                collectionsLocalDataSource.deleteCollection(collectionId)
            }
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
        ).also {
            collectionsLocalDataSource.increaseCollectionItemCount(collectionId)
        }
    }

    override suspend fun removeMovieFromCollection(
        collectionId: Int,
        movieId: Int
    ) = safeCall {
        collectionsRemoteDataSource.removeMovieFromCollection(
            collectionId,
            movieId
        ).also {
            collectionsLocalDataSource.decreaseCollectionItemCount(collectionId)
        }
    }

    override suspend fun getCollectionMovies(
        collectionId: Int
    ): List<Movie> = safeCall {
        collectionsRemoteDataSource.getCollectionMovies(collectionId)
            .map(CollectionItemDto::toMovie)
    }

    override suspend fun clearCollectionsCache() =
        collectionsLocalDataSource.clearCollectionsCache()
}