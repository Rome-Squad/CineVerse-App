package com.giraffe.person.usecase

import com.giraffe.person.entity.PersonDetails
import com.giraffe.person.repository.PersonRepository

class GetPersonDetailsUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personId: Int): PersonDetails {
        return repository.getPersonDetails(personId)
    }
}