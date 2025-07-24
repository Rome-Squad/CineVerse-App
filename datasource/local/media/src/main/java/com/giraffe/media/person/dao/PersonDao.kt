package com.giraffe.media.person.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.relations.MoviePersonCrossRef
import com.giraffe.media.person.relations.SeriesPersonCrossRef
import com.giraffe.media.utils.DatabaseConstants.PERSONS_TABLE

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun storePerson(person: PersonCacheDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeople(people: List<PersonCacheDto>)

    @Query("""
        SELECT p.* FROM persons p
        INNER JOIN MOVIE_PERSON_CROSS_REF_TABLE mp
        ON p.id = mp.personId
        WHERE mp.movieId = :movieId
    """)
    suspend fun getPeopleByMovieId(movieId: Int): List<PersonCacheDto>

    @Query("""
        SELECT p.* FROM persons p
        INNER JOIN SERIES_PERSON_CROSS_REF_TABLE sp
        ON p.id = sp.personId
        WHERE sp.seriesId = :seriesId
    """)
    suspend fun getPeopleBySeriesId(seriesId: Int): List<PersonCacheDto>

    @Query("SELECT * FROM $PERSONS_TABLE WHERE name LIKE '%' || :personName || '%' AND page = :page")
    suspend fun searchByName(personName: String, page: Int): List<PersonCacheDto>

    @Query("SELECT * FROM $PERSONS_TABLE WHERE id = :id")
    suspend fun getPersonById(id: Int): PersonCacheDto

    @Query("SELECT * FROM $PERSONS_TABLE WHERE name = :personName LIMIT 1")
    suspend fun getPersonByName(personName: String): PersonCacheDto

    @Query("SELECT * FROM $PERSONS_TABLE WHERE isRecent = 1")
    suspend fun getRecentPeople(): List<PersonCacheDto>

    @Query("DELETE FROM $PERSONS_TABLE WHERE isRecent = 1")
    suspend fun clearRecentPeople()

    @Query("""DELETE FROM $PERSONS_TABLE WHERE isRecent = 0 AND cachedAt <= :currentTime - 3600000""")
    suspend fun clearPersonCache(currentTime: Long)

    @Transaction
    suspend fun insertPeopleForMovie(people: List<PersonCacheDto>, movieId: Int) {
        insertPeople(people)
        val crossRefs = people.map {
            MoviePersonCrossRef(movieId = movieId, personId = it.id)
        }
        insertMoviePersonCrossRefs(crossRefs)
    }

    @Transaction
    suspend fun insertPeopleForSeries(people: List<PersonCacheDto>, seriesId: Int) {
        insertPeople(people)
        val crossRefs = people.map {
            SeriesPersonCrossRef(seriesId = seriesId, personId = it.id)
        }
        insertSeriesPersonCrossRefs(crossRefs)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMoviePersonCrossRefs(refs: List<MoviePersonCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSeriesPersonCrossRefs(refs: List<SeriesPersonCrossRef>)
}