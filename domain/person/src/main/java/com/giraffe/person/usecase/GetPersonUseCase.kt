package com.giraffe.person.usecase

import com.giraffe.person.repository.PersonsRepository

class GetPersonUseCase(
    private val repository: PersonsRepository
) {
    suspend fun getByName(personName: String) = repository.getPersonByName(personName)
    suspend fun getById(id: Int) = repository.getPersonById(id)
}