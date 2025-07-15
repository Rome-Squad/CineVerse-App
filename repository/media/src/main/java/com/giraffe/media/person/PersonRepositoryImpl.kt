package com.giraffe.media.person

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.local.PersonLocalDataSource
import com.giraffe.media.person.remote.PersonRemoteDataSource
import com.giraffe.media.person.remote.response.CreditsResponse
import com.giraffe.media.person.repository.PersonRepository
import com.giraffe.media.person.util.SafeCall
import com.giraffe.media.person.util.toDto
import com.giraffe.media.person.util.toEntity
import com.giraffe.media.person.util.toImageList
import com.giraffe.media.person.util.toPersonMovieCredits
import com.giraffe.media.person.util.toPersonTvCredits
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PersonRepositoryImpl(
    private val remoteDataSource: PersonRemoteDataSource,
    private val localDataSource: PersonLocalDataSource,
) : PersonRepository {

    override suspend fun searchByName(personName: String) = SafeCall {
        localDataSource.searchByName(personName).map { it.toEntity() }.ifEmpty {
            val people = remoteDataSource.searchByName(personName).people
                .map { it.toEntity() }

            val peopleDto = people.map { it.toDto() }

            localDataSource.storePeople(peopleDto)

            people
        }
    }

    override suspend fun storeRecentPerson(person: Person) =
        SafeCall { localDataSource.storePerson(person.toDto().copy(isRecent = true)) }

    override suspend fun getRecentPeople() = SafeCall {
        localDataSource.getRecentPeople().map { it.toEntity() }
    }

    override suspend fun clearRecentPeople() = SafeCall {
        localDataSource.clearRecentPeople()
    }

    private suspend fun handle(
        id: Int,
        isMovie: Boolean,
        getPeople: suspend () -> List<Person>,
        exe: suspend () -> CreditsResponse
    ): List<Person> {
        return getPeople().ifEmpty {
            val response = exe()
            val cast = response.cast.map { it.toEntity(PersonType.CAST) }
            val crew = response.crew.map { it.toEntity(PersonType.CREW) }
            val people = cast + crew
            val peopleDto = people.map {
                if (isMovie) it.toDto(movieId = id) else it.toDto(seriesId = id)
            }
            localDataSource.storePeople(peopleDto)
            people
        }
    }

    override suspend fun getPeopleByMovieId(movieId: Int) = SafeCall {
        handle(
            id = movieId,
            isMovie = true,
            getPeople = { localDataSource.getPeopleByMovieId(movieId).map { it.toEntity() } },
            exe = { remoteDataSource.getCreditsByMovieId(movieId) }
        )
    }

    override suspend fun getPeopleByShowId(seriesId: Int) = SafeCall {
        val localPeople = localDataSource.getPeopleBySeriesId(seriesId).map { it.toEntity() }

        localPeople.ifEmpty {
            val response = remoteDataSource.getCreditsBySeriesId(seriesId)

            val cast = response.cast.map { it.toEntity(PersonType.CAST) }
            val crew = response.crew.map { it.toEntity(PersonType.CREW) }

            val people = cast + crew

            val peopleDto = people.map { it.toDto(seriesId = seriesId) }

            localDataSource.storePeople(peopleDto)

            people
        }
    }

    override suspend fun getPersonDetails(personId: Int) = SafeCall {
        withContext(Dispatchers.IO) {
            val details = async { remoteDataSource.getPersonDetails(personId) }
            val images = async { remoteDataSource.getPersonImages(personId) }
            val movies = async { remoteDataSource.getPersonMovieCredits(personId) }
            val shows = async { remoteDataSource.getPersonTvCredits(personId) }
            Person(
                id = personId,
                name = details.await().name,
                role = details.await().knownForDepartment,
                images = images.await().toImageList(),
                movieCredits = movies.await().cast.toPersonMovieCredits(),
                tvCredits = shows.await().cast.toPersonTvCredits()
            )
        }
    }
}