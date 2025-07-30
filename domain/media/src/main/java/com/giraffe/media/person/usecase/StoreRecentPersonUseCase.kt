package com.giraffe.media.person.usecase

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.repository.PersonRepository
import javax.inject.Inject

class StoreRecentPersonUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(person: Person) = repository.storeRecentPerson(person)
}