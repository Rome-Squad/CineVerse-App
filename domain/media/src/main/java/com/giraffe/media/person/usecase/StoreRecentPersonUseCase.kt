package com.giraffe.media.person.usecase

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.repository.PersonRepository

class StoreRecentPersonUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(person: Person) = repository.storeRecentPerson(person)
}