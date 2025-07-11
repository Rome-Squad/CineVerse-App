package com.giraffe.person.usecase

import com.giraffe.person.exception.SearchResultNotFoundException
import com.giraffe.person.repository.PersonRepository

class SearchPeopleByNameUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personName: String) = repository.searchByName(personName).ifEmpty {
        throw SearchResultNotFoundException()
    }
}