package com.giraffe.cineverseapp.data.database

import MovieDao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.giraffe.explore.dao.ExploreSearchKeywordDao
import com.giraffe.explore.model.SearchKeywordCacheDto
import com.giraffe.movie.dto.MovieDto

@Database(
    entities = [
        MovieDto::class,
        SearchKeywordCacheDto::class
    ],
    version = 1
)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun exploreSearchKeywordDao(): ExploreSearchKeywordDao
}