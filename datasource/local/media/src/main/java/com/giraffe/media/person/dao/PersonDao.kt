package com.giraffe.media.person.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.media.person.local.cacheDto.PersonCacheDto

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun storePerson(person: PersonCacheDto)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeople(people: List<PersonCacheDto>)
    @Query("SELECT * FROM persons WHERE seriesId = :seriesId")
    suspend fun getPeopleBySeriesId(seriesId: Int): List<PersonCacheDto>
    @Query("SELECT * FROM persons WHERE movieId = :movieId")
    suspend fun getPeopleByMovieId(movieId: Int): List<PersonCacheDto>

    @Query("SELECT * FROM persons WHERE name LIKE '%' || :personName || '%'")
    suspend fun searchByName(personName: String): List<PersonCacheDto>

    @Query("SELECT * FROM persons WHERE id = :id")
    suspend fun getPersonById(id: Int): PersonCacheDto

    @Query("SELECT * FROM persons WHERE name = :personName LIMIT 1")
    suspend fun getPersonByName(personName: String): PersonCacheDto

    @Query("SELECT * FROM persons WHERE isRecent = 1")
    suspend fun getRecentPeople(): List<PersonCacheDto>

    @Query("DELETE FROM persons WHERE isRecent = 1")
    suspend fun clearRecentPeople()

    @Query("""DELETE FROM persons WHERE isRecent = 0 AND cachedAt <= :currentTime - 3600000""")
    suspend fun clearPersonCache(currentTime: Long)
}