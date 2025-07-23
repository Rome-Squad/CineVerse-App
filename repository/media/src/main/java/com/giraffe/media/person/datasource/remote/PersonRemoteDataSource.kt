package com.giraffe.media.person.datasource.remote

import com.giraffe.media.person.datasource.remote.dto.CreditsDto
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto
import com.giraffe.media.person.datasource.remote.dto.SearchPersonDto

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String, page: Int): SearchPersonDto
    suspend fun getCreditsBySeriesId(seriesId: Int): CreditsDto
    suspend fun getCreditsByMovieId(movieId: Int): CreditsDto
    suspend fun getPersonDetails(personId: Int): PersonDetailsDto
    suspend fun getPersonImages(personId: Int): PersonProfileImageDto
    suspend fun getPersonMediaCredits(personId: Int): List<PersonCreditDto>
    suspend fun getPersonSocialMedia(personId: Int): PersonSocialMediaDto
}