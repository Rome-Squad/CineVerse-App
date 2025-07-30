package com.giraffe.media.person.usecase

import com.giraffe.media.person.repository.PersonRepository
import javax.inject.Inject

class SearchPeopleByNameUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personName: String, page: Int) =
        repository.searchByName(personName, page)
}