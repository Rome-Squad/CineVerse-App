package com.giraffe.media.person.datasource.remote

import com.giraffe.media.person.datasource.remote.dto.CreditsDto
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonDto
import com.giraffe.media.person.datasource.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String,page:Int): List<PersonDto>
    suspend fun getCreditsBySeriesId(seriesId: Int): CreditsDto
    suspend fun getCreditsByMovieId(movieId: Int): CreditsDto
    suspend fun getPersonDetails(personId: Int): PersonDetailsDto
    suspend fun getPersonImages(personId: Int): PersonProfileImageDto
    suspend fun getPersonMediaCredits(personId: Int): List<PersonCreditDto>
    suspend fun getPersonSocialMedia(personId: Int): PersonSocialMediaDto

}