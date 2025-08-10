package com.giraffe.media.mediaMember.retrofit

import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
import com.giraffe.media.util.RetrofitRequestBuilder
import javax.inject.Inject

class MediaMemberRemoteDataSourceImplRetrofit @Inject constructor(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<MediaMemberApiServiceRetrofit>
) : MediaMemberRemoteDataSource {

    override suspend fun searchForActorByName(personName: String, page: Int) =
        retrofitRequestBuilder.get { searchByName(name = personName, page = page) }.mediaMembers
            .filter { it.department == ACTOR_DEPARTMENT }

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

    private companion object {
        const val ACTOR_DEPARTMENT = "Acting"
    }
}