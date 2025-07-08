package com.giraffe.person.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.person.entity.PersonEntity

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun storePerson(person: PersonEntity)

    @Query("SELECT * FROM persons WHERE name LIKE '%' || :personName || '%'")
    suspend fun searchByName(personName: String): List<PersonEntity>

    @Query("SELECT * FROM persons WHERE id = :id")
    suspend fun getPersonById(id: Int): PersonEntity

    @Query("SELECT * FROM persons WHERE name = :personName LIMIT 1")
    suspend fun getPersonByName(personName: String): PersonEntity
}