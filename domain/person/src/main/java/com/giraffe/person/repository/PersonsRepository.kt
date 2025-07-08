package com.giraffe.person.repository

import com.giraffe.person.entity.Person

interface PersonsRepository {
    suspend fun searchPersonByName(personName: String): List<Person>
}