package com.giraffe.media.mediaMember.retrofit

import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.CreditsDto
import com.giraffe.media.person.datasource.remote.dto.MediaMemberDto
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto
import com.giraffe.media.util.RetrofitRequestBuilder
import javax.inject.Inject

class MediaMemberRemoteDataSourceImplRetrofit @Inject constructor(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<MediaMemberApiServiceRetrofit>
) : MediaMemberRemoteDataSource {

    override suspend fun searchForActorByName(
        personName: String,
        page: Int
    ): List<MediaMemberDto> {
        return retrofitRequestBuilder
            .get { searchByName(name = personName, page = page) }
            .mediaMembers
            .filter { it.department == ACTOR_DEPARTMENT }
    }

    override suspend fun getPersonMoviesById(personId: Int): List<PersonCreditDto> {
        return retrofitRequestBuilder
            .get { getPersonMediaCredits(personId) }
            .mediaCredits
            .filter { it.mediaType == MOVIE_MEDIA_TYPE }
    }

    override suspend fun getPersonSeriesById(personId: Int): List<PersonCreditDto> {
        return retrofitRequestBuilder
            .get { getPersonMediaCredits(personId) }
            .mediaCredits
            .filter { it.mediaType == SERIES_MEDIA_TYPE }
    }

    override suspend fun getPersonSocialMedia(personId: Int): PersonSocialMediaDto {
        return retrofitRequestBuilder.get { getPersonSocialMedia(personId) }
    }

    override suspend fun getPersonDetails(personId: Int): PersonDetailsDto {
        return retrofitRequestBuilder.get { getPersonDetails(personId) }
    }

    override suspend fun getPersonImages(personId: Int): PersonProfileImageDto {
        return retrofitRequestBuilder.get { getPersonImages(personId) }
    }

    override suspend fun getCreditsBySeriesId(seriesId: Int): CreditsDto {
        return retrofitRequestBuilder.get { getCreditsBySeriesId(seriesId) }
    }

    override suspend fun getCreditsByMovieId(movieId: Int): CreditsDto {
        return retrofitRequestBuilder.get { getCreditsByMovieId(movieId) }
    }

    private companion object {
        const val ACTOR_DEPARTMENT = "Acting"
        const val MOVIE_MEDIA_TYPE = "movie"
        const val SERIES_MEDIA_TYPE = "tv"
    }
}