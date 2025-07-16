package com.giraffe.media.person.datasource.remote

import com.giraffe.media.person.model.dto.CreditsResponse
import com.giraffe.media.person.model.dto.PersonDetailsResponse
import com.giraffe.media.person.model.dto.PersonMovieCreditsResponse
import com.giraffe.media.person.model.dto.PersonProfileImageResponse
import com.giraffe.media.person.model.dto.PersonTvCastItemResponse
import com.giraffe.media.person.model.dto.SearchPersonResponse

interface PersonRemoteDataSource {
    suspend fun searchByName(personName: String): SearchPersonResponse
    suspend fun getCreditsBySeriesId(seriesId: Int): CreditsResponse
    suspend fun getCreditsByMovieId(movieId: Int): CreditsResponse
    suspend fun getPersonDetails(personId: Int): PersonDetailsResponse
    suspend fun getPersonImages(personId: Int): PersonProfileImageResponse
    suspend fun getPersonMovieCredits(personId: Int): PersonMovieCreditsResponse

    suspend fun getPersonTvCredits(personId: Int): PersonTvCastItemResponse
}