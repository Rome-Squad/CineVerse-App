package com.giraffe.media.person.repository

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit

interface PersonRepository {
    suspend fun searchByName(personName: String, page: Int): List<Person>
    suspend fun addRecentPerson(person: Person)
    suspend fun getRecentPeople(): List<Person>
    suspend fun clearRecentPeople()
    suspend fun getPeopleByMovieId(movieId: Int): List<Person>
    suspend fun getPeopleByShowId(seriesId: Int): List<Person>
    suspend fun getPersonDetails(personId: Int): Person
    suspend fun getPeopleMediaCredits(personId: Int): List<PersonCredit>
    suspend fun getPersonImages(personId: Int): List<String>
}