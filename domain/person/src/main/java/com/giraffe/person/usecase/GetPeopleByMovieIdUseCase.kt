package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.repository.PersonRepository

class GetPeopleByMovieIdUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(movieId: Int): List<Person> {
        return repository.getPeopleByMovieId(movieId)
    }
}