package com.giraffe.person.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giraffe.person.dao.PersonDao
import com.giraffe.person.entity.PersonEntity

@Database(
    entities = [PersonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}