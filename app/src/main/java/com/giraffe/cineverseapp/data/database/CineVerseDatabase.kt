package com.giraffe.cineverseapp.data.database

import MovieDao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.model.SearchKeywordCacheDto
import com.giraffe.movie.converter.Converters
import com.giraffe.movie.dao.MoviesSearchHistoryDao
import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.person.dao.PersonDao
import com.giraffe.person.local.dto.PersonDto

@Database(
    entities = [
        SearchKeywordCacheDto::class,
        MovieCacheDto::class,
        MovieGenreCacheDto::class,
        PersonDto::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun moviesSearchHistoryDao(): MoviesSearchHistoryDao

    abstract fun exploreSearchKeywordDao(): ExploreSearchKeywordDao
    abstract fun personDao(): PersonDao
}