package com.giraffe.person.repository

import com.giraffe.person.entity.Person

interface PersonsRepository {
    suspend fun searchByName(personName: String): List<Person>
    suspend fun storePerson(person: Person)
    suspend fun getPersonById(id: Int): Person
    suspend fun getPersonByName(personName: String): Person
}