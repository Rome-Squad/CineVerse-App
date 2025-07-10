package com.giraffe.cineverseapp.data.database

import MovieDao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.giraffe.movie.dto.MovieDto
import com.giraffe.person.dao.PersonDao
import com.giraffe.person.entity.PersonDto

@Database(entities = [MovieDto::class,PersonDto::class], version = 1)
abstract class CineVerseDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun personDao(): PersonDao
}