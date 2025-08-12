package com.giraffe.cineverseapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.giraffe.cineverseapp.data.database.converter.Converters
import com.giraffe.media.explore.dao.SearchKeywordDao
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.movie.dao.MovieDao
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.person.dao.PersonDao
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.relations.MoviePersonCrossRef
import com.giraffe.media.person.relations.SeriesPersonCrossRef
import com.giraffe.media.series.dao.SeriesDao
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
        SeriesCacheDto::class,
        SeriesGenreCacheDto::class,
        PopularSeriesCacheDto::class,
        RecentlyReleasedSeriesCacheDto::class,
        TopRatedSeriesCacheDto::class,
        RecentViewedSeriesCacheDto::class,
        PersonCacheDto::class,
        MoviePersonCrossRef::class,
        SeriesPersonCrossRef::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
    abstract fun personDao(): PersonDao
    abstract fun exploreSearchKeywordDao(): SearchKeywordDao
}