package com.giraffe.media.person.datasource.remote

import com.giraffe.media.person.model.dto.CreditsDto
import com.giraffe.media.person.model.dto.PersonCreditDto
import com.giraffe.media.person.model.dto.PersonDetailsDto
import com.giraffe.media.person.model.dto.PersonDto
import com.giraffe.media.person.model.dto.PersonProfileImageDto
import com.giraffe.media.person.model.dto.PersonSocialMediaDto

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String): List<PersonDto>
    suspend fun getCreditsBySeriesId(seriesId: Int): CreditsDto
    suspend fun getCreditsByMovieId(movieId: Int): CreditsDto
    suspend fun getPersonDetails(personId: Int): PersonDetailsDto
    suspend fun getPersonImages(personId: Int): PersonProfileImageDto
    suspend fun getPersonMediaCredits(personId: Int): List<PersonCreditDto>
    suspend fun getPersonSocialMedia(personId: Int): PersonSocialMediaDto

}