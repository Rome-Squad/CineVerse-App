package com.giraffe.person.repository

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.PersonDetails

interface PersonRepository {
    suspend fun searchByName(personName: String): List<Person>
    suspend fun storeRecentPerson(person: Person)
    suspend fun getRecentPeople(): List<Person>
    suspend fun clearRecentPeople()
    suspend fun getPeopleByMovieId(movieId: Int): List<Person>

    suspend fun getPeopleByShowId(seriesId: Int): List<Person>
    suspend fun getPersonDetails(personId: Int): PersonDetails
}