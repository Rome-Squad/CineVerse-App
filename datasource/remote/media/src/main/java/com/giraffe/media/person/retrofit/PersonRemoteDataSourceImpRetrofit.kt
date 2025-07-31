package com.giraffe.media.person.retrofit

import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.util.RetrofitRequestBuilder
import javax.inject.Inject

class PersonRemoteDataSourceImplRetrofit @Inject constructor(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<PersonApiServiceRetrofit>
) : PersonRemoteDataSource {

    override suspend fun searchByName(personName: String, page: Int) =
        retrofitRequestBuilder.get { searchByName(name = personName, page = page) }.people

    override suspend fun getPersonMediaCredits(personId: Int) =
        retrofitRequestBuilder.get { getPersonMediaCredits(personId) }.mediaCredits

    override suspend fun getPersonSocialMedia(personId: Int) =
        retrofitRequestBuilder.get { getPersonSocialMedia(personId) }

    override suspend fun getPersonDetails(personId: Int) =
        retrofitRequestBuilder.get { getPersonDetails(personId) }

    override suspend fun getPersonImages(personId: Int) =
        retrofitRequestBuilder.get { getPersonImages(personId) }

    override suspend fun getCreditsBySeriesId(seriesId: Int) =
        retrofitRequestBuilder.get { getCreditsBySeriesId(seriesId) }

    override suspend fun getCreditsByMovieId(movieId: Int) =
        retrofitRequestBuilder.get { getCreditsByMovieId(movieId) }
}