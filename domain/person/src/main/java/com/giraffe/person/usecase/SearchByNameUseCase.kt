package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.repository.PersonRepository

class SearchByNameUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personName: String): List<Person> =
        repository.searchByName(personName)
}