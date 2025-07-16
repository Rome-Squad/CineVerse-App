package com.giraffe.cineverseapp.data.database

import MovieDao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.giraffe.cineverseapp.data.database.converter.Converters
import com.giraffe.media.explore.model.SearchKeywordCacheDto
import com.giraffe.media.explore.dao.ExploreSearchKeywordDao
import com.giraffe.media.person.dao.PersonDao
import com.giraffe.media.person.model.cache.PersonCacheDto
import com.giraffe.media.series.dao.SeriesDao
import com.giraffe.media.series.model.CachedSeasonDto
import com.giraffe.media.series.model.CachedSeriesDto
import com.giraffe.media.series.model.CachedSeriesGenreDto
import  com.giraffe.media.movie.model.cache.MovieCacheDto
import  com.giraffe.media.movie.model.cache.MovieGenreCacheDto
import com.giraffe.media.person.relations.MoviePersonCrossRef
import com.giraffe.media.person.relations.SeriesPersonCrossRef

@Database(
    entities = [
        SearchKeywordCacheDto::class,
        MovieCacheDto::class,
        MovieGenreCacheDto::class,
        CachedSeriesDto::class,
        CachedSeasonDto::class,
        CachedSeriesGenreDto::class,
        PersonCacheDto::class,
        MoviePersonCrossRef::class,
        SeriesPersonCrossRef::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun exploreSearchKeywordDao(): ExploreSearchKeywordDao
    abstract fun seriesDao(): SeriesDao
    abstract fun personDao(): PersonDao
}