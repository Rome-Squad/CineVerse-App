package com.giraffe.person.usecase

import com.giraffe.person.repository.PersonRepository

class GetPersonUseCase(
    private val repository: PersonRepository
) {
    suspend fun getByName(personName: String) = repository.getPersonByName(personName)
    suspend fun getById(id: Int) = repository.getPersonById(id)
}