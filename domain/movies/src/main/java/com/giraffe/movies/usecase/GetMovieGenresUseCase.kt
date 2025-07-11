package com.giraffe.movies.usecase

import com.giraffe.movies.repository.MoviesRepository

class GetMovieGenresUseCase (
    private val repository: MoviesRepository
){
    suspend operator fun invoke(genreIDs : List<Int>) : List<String> = repository.getMovieGenres(genreIDs)
}