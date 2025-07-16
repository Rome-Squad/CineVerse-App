package com.giraffe.media.person.usecase

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.repository.PersonRepository

class GetPeopleBySeriesIdUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(seriesId: Int): List<Person> {
        return repository.getPeopleBySeriesId(seriesId)
    }
}