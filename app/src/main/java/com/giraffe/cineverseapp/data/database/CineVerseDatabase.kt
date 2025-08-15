package com.giraffe.cineverseapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.giraffe.cineverseapp.data.database.converter.Converters
import com.giraffe.media.collections.dao.CollectionsDao
import com.giraffe.media.collections.datasource.local.cache.CollectionCacheDto
import com.giraffe.media.explore.dao.SearchKeywordDao
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.movie.datasource.local.cacheDto.MatchesYourVibeMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.PopularMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentReleasedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentlyViewedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.UpcomingMovieCacheDto
import com.giraffe.media.person.dao.PersonDao
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.relations.MoviePersonCrossRef
import com.giraffe.media.person.relations.SeriesPersonCrossRef
import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.datasource.local.cacheDto.MatchesYourVibeSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.PopularSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.RecentViewedSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.RecentlyReleasedSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesGenreCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.TopRatedSeriesCacheDto

@Database(
    entities = [
        SearchKeywordCacheDto::class,
        MovieCacheDto::class,
        MovieGenreCacheDto::class,
        PopularMovieCacheDto::class,
        RecentReleasedMovieCacheDto::class,
        UpcomingMovieCacheDto::class,
        RecentlyViewedMovieCacheDto::class,
        MatchesYourVibeMovieCacheDto::class,
        SeriesCacheDto::class,
        SeriesGenreCacheDto::class,
        PopularSeriesCacheDto::class,
        RecentlyReleasedSeriesCacheDto::class,
        TopRatedSeriesCacheDto::class,
        RecentViewedSeriesCacheDto::class,
        MatchesYourVibeSeriesCacheDto::class,
        PersonCacheDto::class,
        MoviePersonCrossRef::class,
        SeriesPersonCrossRef::class,
        CollectionCacheDto::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
    abstract fun personDao(): PersonDao
    abstract fun exploreSearchKeywordDao(): SearchKeywordDao

    abstract fun collectionsDao(): CollectionsDao
}