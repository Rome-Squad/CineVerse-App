package com.giraffe.media.collections.repository

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series

interface CollectionsRepository {

    suspend fun getCollections(): List<Collection>


    suspend fun getCollectionDetails(collectionId: Int): Collection

    suspend fun addCollection(collection: Collection)

    suspend fun removeCollection(collectionId: Int)

    suspend fun clearCollection(collectionId: Int)


    suspend fun addMovieToCollection(collectionId: Int, movieId: Int)

    suspend fun removeMovieFromCollection(collectionId: Int, movieId: Int)

    suspend fun getCollectionMovies(collectionId: Int): List<Movie>


    suspend fun addSeriesToCollection(collectionId: Int, seriesId: Int)

    suspend fun removeSeriesFromCollection(collectionId: Int, seriesId: Int)

    suspend fun getCollectionSeries(collectionId: Int): List<Series>
}
