package com.giraffe.media.mediaMember.repository

import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.entity.CrewMember
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.series.entity.Series

interface MediaMemberRepository {

    suspend fun addCastToRecentViewed(castMember: CastMember)

    suspend fun addCrewToRecentViewed(crewMember: CrewMember)

    suspend fun clearRecentViewed()

    suspend fun getRecentMediaMembers(): MediaMembers

    suspend fun getMediaMembersByMovieId(movieId: Int): MediaMembers

    suspend fun getMediaMembersBySeriesId(seriesId: Int): MediaMembers

    suspend fun getImagesUrlById(id: Int): List<String>

    suspend fun getCastDetailsById(id: Int): CastMember

    suspend fun getCrewDetailsByCrewId(id: Int): CrewMember

    suspend fun getCastCreditsById(id: Int): CastMedia

    suspend fun getActorByName(name: String, page: Int): List<CastMember>

    data class MediaMembers(
        val cast: List<CastMember>,
        val crew: List<CrewMember>
    )

    data class CastMedia(
        val movies: List<Movie>,
        val series: List<Series>,
    )
}