package com.giraffe.media.person.usecase

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.repository.PersonRepository

class GetPeopleByMovieIdUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(movieId: Int): List<Person> {
        return repository.getPeopleByMovieId(movieId)
    }
}