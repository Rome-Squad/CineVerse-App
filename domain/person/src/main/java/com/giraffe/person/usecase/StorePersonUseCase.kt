package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.repository.PersonRepository

class StorePersonUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(person: Person) = repository.storePerson(person)
}