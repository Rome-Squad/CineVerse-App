package com.giraffe.media.collections.datasource.remote

import com.giraffe.media.collections.datasource.remote.dto.CollectionDto
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.series.datasource.remote.dto.SeriesDto

interface CollectionsRemoteDataSource {


    suspend fun getCollections(): List<CollectionDto>


    suspend fun getCollectionDetails(collectionId: Int): CollectionDto

    suspend fun addCollection(collection: CollectionDto)

    suspend fun removeCollection(collectionId: Int)

    suspend fun clearCollection(collectionId: Int)


    suspend fun addMovieToCollection(collectionId: Int, movieId: Int)

    suspend fun removeMovieFromCollection(collectionId: Int, movieId: Int)

    suspend fun getCollectionMovies(collectionId: Int): List<MovieDto>


    suspend fun addSeriesToCollection(collectionId: Int, seriesId: Int)

    suspend fun removeSeriesFromCollection(collectionId: Int, seriesId: Int)

    suspend fun getCollectionSeries(collectionId: Int): List<SeriesDto>
}