package com.giraffe.media.person.usecase

import com.giraffe.media.person.repository.PersonRepository

class GetPersonImagesUseCase(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(personId: Int) = repository.getPersonImages(personId)
}