package com.giraffe.cineverseapp.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE movie_genre_table ADD COLUMN count INTEGER NOT NULL DEFAULT 0")
    }
}