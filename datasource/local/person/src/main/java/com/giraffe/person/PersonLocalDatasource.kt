package com.giraffe.person

import com.giraffe.person.entity.PersonDto

interface PersonLocalDatasource {
    suspend fun storePerson(person: PersonDto)
    suspend fun searchByName(personName: String): List<PersonDto>
    suspend fun getPersonById(id: Int): PersonDto
    suspend fun getPersonByName(personName: String): PersonDto
    suspend fun getRecentPeople(): List<PersonDto>
}