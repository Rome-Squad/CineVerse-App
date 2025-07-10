package com.giraffe.cineverseapp.data.database

import MovieDao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.model.SearchKeywordCacheDto
import com.giraffe.movie.dto.MovieDto
import com.giraffe.series.database.SearchCacheDao
import com.giraffe.series.database.SeriesDao
import com.giraffe.series.dto.SearchCacheEntity
import com.giraffe.series.dto.SeasonEntity
import com.giraffe.series.dto.SeriesEntity
import com.giraffe.series.dto.SeriesGenreEntity

@Database(
    entities = [
        MovieDto::class,
        SearchKeywordCacheDto::class,
        SeriesEntity::class,
        SeasonEntity::class,
        SeriesGenreEntity::class,
        SearchCacheEntity::class
    ],
    version = 1
)abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun exploreSearchKeywordDao(): ExploreSearchKeywordDao
    abstract fun seriesDao(): SeriesDao
    abstract fun searchCacheDao(): SearchCacheDao
}