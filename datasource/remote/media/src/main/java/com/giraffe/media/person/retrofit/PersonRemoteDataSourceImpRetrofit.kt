package com.giraffe.media.person.retrofit

import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.PersonDto
import com.giraffe.media.util.RetrofitRequestBuilder

class PersonRemoteDataSourceImplRetrofit(
    private val RetrofitRequestBuilder: RetrofitRequestBuilder<PersonApiServiceRetrofit>
) : PersonRemoteDataSource {

    override suspend fun searchByName(personName: String): List<PersonDto> =
        RetrofitRequestBuilder.get { searchByName(personName) }.people

    override suspend fun getPersonMediaCredits(personId: Int) =
        RetrofitRequestBuilder.get { getPersonMediaCredits(personId) }.mediaCredits

    override suspend fun getPersonSocialMedia(personId: Int) =
        RetrofitRequestBuilder.get { getPersonSocialMedia(personId) }

    override suspend fun getPersonDetails(personId: Int) =
        RetrofitRequestBuilder.get { getPersonDetails(personId) }

    override suspend fun getPersonImages(personId: Int) =
        RetrofitRequestBuilder.get { getPersonImages(personId) }

    override suspend fun getCreditsBySeriesId(seriesId: Int) =
        RetrofitRequestBuilder.get { getCreditsBySeriesId(seriesId) }

    override suspend fun getCreditsByMovieId(movieId: Int) =
        RetrofitRequestBuilder.get { getCreditsByMovieId(movieId) }
}
