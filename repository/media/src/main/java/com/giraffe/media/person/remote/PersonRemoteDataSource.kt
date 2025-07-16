package com.giraffe.media.person.remote

import com.giraffe.media.person.remote.dto.CreditsDto
import com.giraffe.media.person.remote.dto.PersonDetailsDto
import com.giraffe.media.person.remote.dto.PersonDto
import com.giraffe.media.person.remote.dto.PersonMovieCastItemDto
import com.giraffe.media.person.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.remote.dto.PersonTvCastDto

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String): List<PersonDto>
    suspend fun getCreditsBySeriesId(seriesId: Int): CreditsDto
    suspend fun getCreditsByMovieId(movieId: Int): CreditsDto
    suspend fun getPersonDetails(personId: Int): PersonDetailsDto
    suspend fun getPersonImages(personId: Int): PersonProfileImageDto
    suspend fun getPersonMovieCredits(personId: Int): List<PersonMovieCastItemDto>

    suspend fun getPersonTvCredits(personId: Int): List<PersonTvCastDto>
}