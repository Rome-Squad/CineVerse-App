package com.giraffe.media.person.usecase

import com.giraffe.media.person.repository.PersonRepository

class SearchPeopleByNameUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personName: String, page: Int) =
        repository.searchByName(personName, page)
}