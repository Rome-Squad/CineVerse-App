package com.giraffe.person.local

import com.giraffe.person.local.dto.PersonDto

interface PersonLocalDataSource {
    suspend fun storePerson(person: PersonDto)
    suspend fun searchByName(personName: String): List<PersonDto>
    suspend fun getPersonById(id: Int): PersonDto
    suspend fun getPersonByName(personName: String): PersonDto
    suspend fun getRecentPeople(): List<PersonDto>
    suspend fun clearRecentPeople()
    suspend fun getPeopleByShowId(showId: Int): List<PersonDto>
    suspend fun getPeopleByMovieId(movieId: Int): List<PersonDto>

}