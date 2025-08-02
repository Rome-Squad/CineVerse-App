package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class AddGenresUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genres: List<Genre>) {
        repository.addGenres(genres)
    }
}