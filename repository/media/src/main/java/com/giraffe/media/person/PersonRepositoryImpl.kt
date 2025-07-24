package com.giraffe.media.person

import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.PersonDto
import com.giraffe.media.person.datasource.remote.dto.ProfileDto
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.mapper.mapToPerson
import com.giraffe.media.person.mapper.toCacheDto
import com.giraffe.media.person.mapper.toEntity
import com.giraffe.media.person.mapper.toImageUrl
import com.giraffe.media.person.repository.PersonRepository
import com.giraffe.media.utils.ContentType
import com.giraffe.media.utils.SafeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PersonRepositoryImpl(
    private val remoteDataSource: PersonRemoteDataSource,
    private val localDataSource: PersonLocalDataSource,
    private val localExploreDataSource: LocalExploreDataSource,
) : PersonRepository {

    override suspend fun searchByName(personName: String, page: Int) = SafeCall {
        withContext(Dispatchers.IO) {
            val keywordData = localExploreDataSource.getSearchKeyword(query = personName)
            val isPageCached = keywordData?.actorsPages?.contains(page) == true
            if (isPageCached) {
                localDataSource.searchByName(personName, page).map(PersonCacheDto::toEntity)
            } else {
                val remoteActors = remoteDataSource.searchByName(personName, page).map(PersonDto::toEntity)
                val updatedKeyword = keywordData?.copy(
                    actorsPages = keywordData.actorsPages + page,
                    searchedAt = System.currentTimeMillis()
                ) ?: SearchKeywordCacheDto(
                    keyword = personName,
                    actorsPages = listOf(page)
                )
                localExploreDataSource.insertSearchKeyword(updatedKeyword)
                localDataSource.storePeople(remoteActors.map { it.toCacheDto().copy(page = page) })
                remoteActors
            }
        }
    }

    override suspend fun storeRecentPerson(person: Person) =
        SafeCall { localDataSource.storePerson(person.toCacheDto().copy(isRecent = true)) }

    override suspend fun getRecentPeople() = SafeCall {
        localDataSource.getRecentPeople().map(PersonCacheDto::toEntity)
    }

    override suspend fun clearRecentPeople() = SafeCall {
        localDataSource.clearRecentPeople()
    }


    override suspend fun getPeopleByMovieId(movieId: Int) = SafeCall {
        val content = ContentType.Movie(movieId, remoteDataSource)
        getPeopleForContent(content)
    }

    override suspend fun getPeopleByShowId(seriesId: Int) = SafeCall {
        val content = ContentType.Series(seriesId, remoteDataSource)
        getPeopleForContent(content)
    }

    private suspend fun getPeopleForContent(content: ContentType): List<Person> {
        val cachedPeople = loadPeopleFromCache(content)
        return cachedPeople.ifEmpty { fetchAndCachePeople(content) }
    }

    private suspend fun fetchAndCachePeople(content: ContentType): List<Person> {
        val credits = content.fetchCredits()

        val cast = credits.cast.map { it.toEntity(PersonType.CAST) }
        val crew = credits.crew.map { it.toEntity(PersonType.CREW) }
        val people = cast + crew

        val dtos = people.map { content.toCacheDto(it) }

        localDataSource.storePeople(dtos)

        return people
    }

    private suspend fun loadPeopleFromCache(
        content: ContentType
    ): List<Person> {
        val cachedDtos = when (content) {
            is ContentType.Movie -> localDataSource.getPeopleByMovieId(content.id)
            is ContentType.Series -> localDataSource.getPeopleBySeriesId(content.id)
        }
        return cachedDtos.map(PersonCacheDto::toEntity)
    }

    override suspend fun getPersonDetails(personId: Int) = SafeCall {
        withContext(Dispatchers.IO) {
            val details = async { remoteDataSource.getPersonDetails(personId) }
            val images = async { remoteDataSource.getPersonImages(personId) }
            val media = async { remoteDataSource.getPersonMediaCredits(personId) }
            val socialMedia = async { remoteDataSource.getPersonSocialMedia(personId) }
            mapToPerson(
                personId = personId,
                details = details.await(),
                // The first image is typically the profile image already included in person details.
                // Dropping it prevents duplication in the image list.
                images = images.await().profiles.drop(1),
                media = media.await(),
                socialMedia = socialMedia.await()
            )
        }
    }

    override suspend fun getPeopleMediaCredits(personId: Int) = SafeCall {
        remoteDataSource.getPersonMediaCredits(personId).map(PersonCreditDto::toEntity)
    }

    override suspend fun getPersonImages(personId: Int): List<String> = SafeCall {
        remoteDataSource.getPersonImages(personId).profiles.map(ProfileDto::toImageUrl)
    }
}