package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.repository.PersonRepository

class GetPeopleBySeriesIdUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(seriesId: Int): List<Person> {
        return repository.getPeopleByShowId(seriesId)
    }
}