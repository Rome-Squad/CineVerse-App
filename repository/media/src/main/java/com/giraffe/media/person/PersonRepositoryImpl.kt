package com.giraffe.media.person

import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.mapper.toCacheDto
import com.giraffe.media.person.mapper.toEntity
import com.giraffe.media.person.mapper.toImageList
import com.giraffe.media.person.repository.PersonRepository
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.ContentType
import com.giraffe.media.utils.SafeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PersonRepositoryImpl(
    private val remoteDataSource: PersonRemoteDataSource,
    private val localDataSource: PersonLocalDataSource,
) : PersonRepository {

    override suspend fun searchByName(personName: String, page: Int) = SafeCall {
        remoteDataSource.searchByName(personName, page).toEntity()
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
            Person(
                id = personId,
                name = details.await().name,
                imageUrl = BASE_IMAGE_URL + details.await().profilePath,
                role = details.await().knownForDepartment,
                birthday = details.await().birthday,
                placeOfBirth = details.await().placeOfBirth,
                biography = details.await().biography,
                images = images.await().toImageList(),
                personCredits = media.await().map(PersonCreditDto::toEntity),
                socialMedia = socialMedia.await().toEntity()
            )
        }
    }

    override suspend fun getPeopleMediaCredits(personId: Int) = SafeCall {
        remoteDataSource.getPersonMediaCredits(personId).map(PersonCreditDto::toEntity)
    }
}