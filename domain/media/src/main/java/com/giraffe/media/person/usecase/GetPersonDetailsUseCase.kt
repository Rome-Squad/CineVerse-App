package com.giraffe.media.person.usecase

import com.giraffe.media.person.repository.PersonRepository

class GetPersonDetailsUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personId: Int) = repository.getPersonDetails(personId)
}