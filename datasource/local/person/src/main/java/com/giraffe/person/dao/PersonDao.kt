package com.giraffe.person.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.person.local.dto.PersonDto

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun storePerson(person: PersonDto)

    @Query("SELECT * FROM persons WHERE name LIKE '%' || :personName || '%'")
    suspend fun searchByName(personName: String): List<PersonDto>

    @Query("SELECT * FROM persons WHERE id = :id")
    suspend fun getPersonById(id: Int): PersonDto

    @Query("SELECT * FROM persons WHERE name = :personName LIMIT 1")
    suspend fun getPersonByName(personName: String): PersonDto

    @Query("SELECT * FROM persons WHERE isRecent = 1")
    suspend fun getRecentPeople(): List<PersonDto>

    @Query("DELETE FROM persons WHERE isRecent = 1")
    suspend fun clearRecentPeople()

    @Query(
        """
    DELETE FROM persons 
    WHERE isRecent = 0 
    AND cachedAt <= :currentTime - 3600000
"""
    )
    suspend fun clearCachedPeople(currentTime: Long)
}