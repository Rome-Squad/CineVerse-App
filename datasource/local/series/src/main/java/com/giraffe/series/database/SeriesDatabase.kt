package com.giraffe.series.database

import androidx.room.*
import com.giraffe.series.dto.SearchCacheEntity
import com.giraffe.series.dto.SeasonEntity
import com.giraffe.series.dto.SeriesEntity
import com.giraffe.series.dto.SeriesGenreEntity
import com.giraffe.series.util.Converters

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        SeriesEntity::class,
        SeasonEntity::class,
        SeriesGenreEntity::class,
        SearchCacheEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class SeriesDatabase : RoomDatabase() {
    abstract fun seriesDao(): SeriesDao
    abstract fun searchCacheDao(): SearchCacheDao
}
