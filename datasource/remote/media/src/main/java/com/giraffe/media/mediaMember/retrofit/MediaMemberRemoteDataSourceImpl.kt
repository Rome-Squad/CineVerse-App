package com.giraffe.media.mediaMember.retrofit

import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.CreditsDto
import com.giraffe.media.person.datasource.remote.dto.MediaMemberDto
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto
import com.giraffe.media.util.safeCallRemote
import javax.inject.Inject

class MediaMemberRemoteDataSourceImpl @Inject constructor(
    private val mediaMemberApiService: MediaMemberApiService,
) : MediaMemberRemoteDataSource {

    override suspend fun searchForActorByName(
        personName: String,
        page: Int
    ): List<MediaMemberDto> =
        safeCallRemote { mediaMemberApiService.searchByName(name = personName, page = page) }
            .mediaMembers
            .filter { it.department == ACTOR_DEPARTMENT }


    override suspend fun getPersonMoviesById(personId: Int): List<PersonCreditDto> =
        safeCallRemote { mediaMemberApiService.getPersonMediaCredits(personId) }
            .mediaCredits
            .filter { it.mediaType == MOVIE_MEDIA_TYPE }


    override suspend fun getPersonSeriesById(personId: Int): List<PersonCreditDto> =
        safeCallRemote { mediaMemberApiService.getPersonMediaCredits(personId) }
            .mediaCredits
            .filter { it.mediaType == SERIES_MEDIA_TYPE }


    override suspend fun getPersonSocialMedia(personId: Int): PersonSocialMediaDto =
        safeCallRemote { mediaMemberApiService.getPersonSocialMedia(personId) }


    override suspend fun getPersonDetails(personId: Int): PersonDetailsDto =
        safeCallRemote { mediaMemberApiService.getPersonDetails(personId) }


    override suspend fun getPersonImages(personId: Int): PersonProfileImageDto =
        safeCallRemote { mediaMemberApiService.getPersonImages(personId) }


    override suspend fun getCreditsBySeriesId(seriesId: Int): CreditsDto =
        safeCallRemote { mediaMemberApiService.getCreditsBySeriesId(seriesId) }


    override suspend fun getCreditsByMovieId(movieId: Int): CreditsDto =
        safeCallRemote { mediaMemberApiService.getCreditsByMovieId(movieId) }


    private companion object {
        const val ACTOR_DEPARTMENT = "Acting"
        const val MOVIE_MEDIA_TYPE = "movie"
        const val SERIES_MEDIA_TYPE = "tv"
    }
}