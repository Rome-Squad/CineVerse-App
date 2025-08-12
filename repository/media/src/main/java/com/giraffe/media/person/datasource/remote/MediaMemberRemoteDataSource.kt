package com.giraffe.media.person.datasource.remote

import com.giraffe.media.person.datasource.remote.dto.CreditsDto
import com.giraffe.media.person.datasource.remote.dto.MediaMemberDto
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto

interface MediaMemberRemoteDataSource {

    suspend fun searchForActorByName(personName: String, page: Int): List<MediaMemberDto>

    suspend fun getCreditsBySeriesId(seriesId: Int): CreditsDto

    suspend fun getCreditsByMovieId(movieId: Int): CreditsDto

    suspend fun getPersonDetails(personId: Int): PersonDetailsDto

    suspend fun getPersonImages(personId: Int): PersonProfileImageDto

    suspend fun getPersonMoviesById(personId: Int): List<PersonCreditDto>

    suspend fun getPersonSeriesById(personId: Int): List<PersonCreditDto>

    suspend fun getPersonSocialMedia(personId: Int): PersonSocialMediaDto
}