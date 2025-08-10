package com.giraffe.media.mediaMember.repository

import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.entity.CrewMember

interface MediaMemberRepository {

    suspend fun addCastToRecentViewed(castMember: CastMember)

    suspend fun addCrewToRecentViewed(crewMember: CrewMember)

    suspend fun clearRecentViewed()

    suspend fun getRecentMediaMembers(): MediaMembers

    suspend fun getMediaMembersByMovieId(movieId: Int): MediaMembers

    suspend fun getMediaMembersBySeriesId(seriesId: Int): MediaMembers

    suspend fun getImagesUrlById(id: Int): List<String>

    suspend fun getCastDetailsById(id: Int): CastMember

    suspend fun getCrewDetailsById(id: Int): CrewMember

    suspend fun searchForActorByName(name: String, page: Int): List<CastMember>

    //todo add this to movies and series !!
//    suspend fun getMoviesAndSeriesById(id: Int): List<PersonCredit>

    data class MediaMembers(
        val cast: List<CastMember>,
        val crew: List<CrewMember>
    )
}