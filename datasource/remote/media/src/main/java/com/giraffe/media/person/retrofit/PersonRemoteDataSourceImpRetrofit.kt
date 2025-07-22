package com.giraffe.media.person.retrofit

import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.PersonDto
import com.giraffe.media.util.RetrofitRequestBuilder

class PersonRemoteDataSourceImplRetrofit(
    private val builder: RetrofitRequestBuilder<PersonApiServiceRetrofit>
) : PersonRemoteDataSource {

    override suspend fun searchByName(personName: String): List<PersonDto> =
        builder.get { searchByName(personName) }.people

    override suspend fun getPersonMediaCredits(personId: Int) =
        builder.get { getPersonMediaCredits(personId) }.mediaCredits

    override suspend fun getPersonSocialMedia(personId: Int) =
        builder.get { getPersonSocialMedia(personId) }

    override suspend fun getPersonDetails(personId: Int) =
        builder.get { getPersonDetails(personId) }

    override suspend fun getPersonImages(personId: Int) =
        builder.get { getPersonImages(personId) }

    override suspend fun getCreditsBySeriesId(seriesId: Int) =
        builder.get { getCreditsBySeriesId(seriesId) }

    override suspend fun getCreditsByMovieId(movieId: Int) =
        builder.get { getCreditsByMovieId(movieId) }
}
