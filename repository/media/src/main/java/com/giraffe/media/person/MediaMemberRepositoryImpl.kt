package com.giraffe.media.person

import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.entity.CrewMember
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.person.datasource.local.MediaMemberLocalDataSource
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheType
import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.CastDto
import com.giraffe.media.person.datasource.remote.dto.CreditsDto
import com.giraffe.media.person.datasource.remote.dto.CrewDto
import com.giraffe.media.person.datasource.remote.dto.MediaMemberDto
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.ProfileDto
import com.giraffe.media.person.mapper.mapToCast
import com.giraffe.media.person.mapper.mapToCrew
import com.giraffe.media.person.mapper.mapToMediaMembers
import com.giraffe.media.person.mapper.toCacheDto
import com.giraffe.media.person.mapper.toCastEntity
import com.giraffe.media.person.mapper.toEntity
import com.giraffe.media.person.mapper.toImageUrl
import com.giraffe.media.person.mapper.toMovieEntity
import com.giraffe.media.person.mapper.toSeriesEntity
import com.giraffe.media.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaMemberRepositoryImpl @Inject constructor(
    private val remoteDataSource: MediaMemberRemoteDataSource,
    private val localDataSource: MediaMemberLocalDataSource,
) : MediaMemberRepository {

    override suspend fun getActorByName(name: String, page: Int): List<CastMember> {
        return safeCall {
            remoteDataSource.searchForActorByName(name, page)
                .map(MediaMemberDto::toCastEntity)
        }
    }

    override suspend fun addCastToRecentViewed(castMember: CastMember) {
        return safeCall {
            localDataSource.insertPerson(
                castMember.toCacheDto().copy(isRecent = true)
            )
        }
    }

    override suspend fun addCrewToRecentViewed(crewMember: CrewMember) {
        return safeCall {
            localDataSource.insertPerson(
                crewMember.toCacheDto().copy(isRecent = true)
            )
        }
    }

    override suspend fun getRecentMediaMembers(): MediaMemberRepository.MediaMembers {
        return safeCall {
            localDataSource.getRecentPeople()
                .groupBy { it.type }
                .run {
                    mapToMediaMembers(this)
                }
        }
    }

    override suspend fun clearRecentViewed() {
        return safeCall {
            localDataSource.clearRecentPeople()
        }
    }

    override suspend fun getMediaMembersByMovieId(movieId: Int): MediaMemberRepository.MediaMembers {
        return safeCall {
            localDataSource.getPeopleByMovieId(movieId)
                .ifEmpty {
                    return@safeCall remoteDataSource.getCreditsByMovieId(movieId)
                        .run { storeMediaMembersInCache(this) }
                }
                .groupBy { it.type }
                .run {
                    mapToMediaMembers(
                        cast = this[PersonCacheType.CAST.name].orEmpty(),
                        crew = this[PersonCacheType.CREW.name].orEmpty()
                    )
                }
        }
    }

    override suspend fun getMediaMembersBySeriesId(seriesId: Int): MediaMemberRepository.MediaMembers {
        return safeCall {
            localDataSource.getPeopleBySeriesId(seriesId)
                .ifEmpty {
                    return@safeCall remoteDataSource.getCreditsBySeriesId(seriesId)
                        .run { storeMediaMembersInCache(this) }
                }
                .groupBy { it.type }
                .run {
                    mapToMediaMembers(
                        cast = this[PersonCacheType.CAST.name].orEmpty(),
                        crew = this[PersonCacheType.CREW.name].orEmpty()
                    )
                }
        }
    }

    override suspend fun getImagesUrlById(id: Int): List<String> {
        return safeCall {
            remoteDataSource.getPersonImages(id).profiles.map(ProfileDto::toImageUrl)
        }
    }

    override suspend fun getCastDetailsById(id: Int): CastMember {
        return safeCall {
            withContext(Dispatchers.IO) {
                val details = async { remoteDataSource.getPersonDetails(id) }
                val images = async { remoteDataSource.getPersonImages(id) }
                val socialMedia = async { remoteDataSource.getPersonSocialMedia(id) }

                mapToCast(
                    personId = id,
                    details = details.await(),
                    // The first image is typically the profile image already included in person details.
                    // Dropping it prevents duplication in the image list.
                    images = images.await().profiles.drop(1),
                    socialMedia = socialMedia.await()
                )
            }
        }
    }

    override suspend fun getCrewDetailsByCrewId(id: Int): CrewMember {
        return safeCall {
            withContext(Dispatchers.IO) {
                val details = async { remoteDataSource.getPersonDetails(id) }
                val images = async { remoteDataSource.getPersonImages(id) }
                val socialMedia = async { remoteDataSource.getPersonSocialMedia(id) }

                mapToCrew(
                    personId = id,
                    details = details.await(),
                    // The first image is typically the profile image already included in person details.
                    // Dropping it prevents duplication in the image list.
                    images = images.await().profiles.drop(1),
                    socialMedia = socialMedia.await()
                )
            }
        }
    }

    private fun mapToMediaMembers(
        map: Map<String, List<PersonCacheDto>>
    ): MediaMemberRepository.MediaMembers {

        return mapToMediaMembers(
            cast = map[PersonCacheType.CAST.name].orEmpty(),
            crew = map[PersonCacheType.CREW.name].orEmpty()
        )
    }

    private suspend fun storeMediaMembersInCache(credits: CreditsDto): MediaMemberRepository.MediaMembers {
        return safeCall {
            val crew = credits.crew.map(CrewDto::toEntity)
            val cast = credits.cast.map(CastDto::toEntity)

            MediaMemberRepository.MediaMembers(
                crew = crew,
                cast = cast
            ).also {
                localDataSource.insertPeople(
                    cast.map(CastMember::toCacheDto) + crew.map(CrewMember::toCacheDto)
                )
            }
        }
    }

    override suspend fun getCastCreditsById(id: Int): MediaMemberRepository.CastMedia {
        return safeCall {
            val movies = remoteDataSource.getPersonMoviesById(id)
            val series = remoteDataSource.getPersonSeriesById(id)

            MediaMemberRepository.CastMedia(
                movies = movies.map(PersonCreditDto::toMovieEntity),
                series = series.map(PersonCreditDto::toSeriesEntity)
            )
        }
    }
}