package com.giraffe.cineverseapp.data.database

import MovieDao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.giraffe.movie.datasource.local.cacheDto.MovieDto

@Database(entities = [MovieDto::class], version = 1)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}