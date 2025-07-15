package com.giraffe.person.usecase

import com.giraffe.person.repository.PersonRepository

class GetPersonDetailsUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personId: Int) = repository.getPersonDetails(personId)
}