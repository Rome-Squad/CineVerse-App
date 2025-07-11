package com.giraffe.cineverseapp.data.database

import MovieDao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.giraffe.cineverseapp.data.util.Converters
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.model.SearchKeywordCacheDto
import com.giraffe.movie.dto.MovieDto
import com.giraffe.person.dao.PersonDao
import com.giraffe.person.local.dto.PersonDto
import com.giraffe.series.database.SearchCacheDao
import com.giraffe.series.database.SeriesDao
import com.giraffe.series.model.SearchCacheDto
import com.giraffe.series.model.CachedSeasonDto
import com.giraffe.series.model.CachedSeriesDto
import com.giraffe.series.model.CachedSeriesGenreDto

@Database(
    entities = [
        MovieDto::class,
        SearchKeywordCacheDto::class,
        CachedSeriesDto::class,
        CachedSeasonDto::class,
        CachedSeriesGenreDto::class,
        SearchCacheDto::class,
        PersonDto::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun exploreSearchKeywordDao(): ExploreSearchKeywordDao
    abstract fun seriesDao(): SeriesDao
    abstract fun searchCacheDao(): SearchCacheDao
    abstract fun personDao(): PersonDao
}