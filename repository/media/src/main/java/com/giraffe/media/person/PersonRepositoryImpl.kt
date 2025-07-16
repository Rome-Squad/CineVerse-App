package com.giraffe.media.person

import android.util.Log
import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.mapper.toCacheDto
import com.giraffe.media.person.mapper.toEntity
import com.giraffe.media.person.mapper.toImageList
import com.giraffe.media.person.mapper.toPersonMovieCredits
import com.giraffe.media.person.mapper.toPersonTvCredits
import com.giraffe.media.person.model.dto.CreditsResponse
import com.giraffe.media.person.repository.PersonRepository
import com.giraffe.media.person.util.SafeCall
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

            val peopleDto = people.map { it.toCacheDto() }

            localDataSource.insertPeople(peopleDto)

            people
        }
    }

    override suspend fun storeRecentPerson(person: Person) =
        SafeCall { localDataSource.storePerson(person.toCacheDto().copy(isRecent = true)) }

    override suspend fun getRecentPeople() = SafeCall {
        localDataSource.getRecentPeople().map { it.toEntity() }
    }

    override suspend fun clearRecentPeople() = SafeCall {
        localDataSource.clearRecentPeople()
    }

    override suspend fun getPeopleByMovieId(movieId: Int): List<Person> {
        Log.d("FOLLOWERROR", "Hereeeeee 1: $movieId")
        return getPeople(
            id = movieId,
            isMovie = true,
            getFromLocal = { localDataSource.getPeopleByMovieId(movieId).map { it.toEntity() } },
            getFromRemote = { remoteDataSource.getCreditsByMovieId(movieId) }
        )
    }

    override suspend fun getPeopleBySeriesId(seriesId: Int) = SafeCall {

        getPeople(
            id = seriesId,
            isMovie = false,
            getFromLocal = { localDataSource.getPeopleBySeriesId(seriesId).map { it.toEntity() }},
            getFromRemote = { remoteDataSource.getCreditsBySeriesId(seriesId) }
        )
    }

    private suspend fun getPeople(
        id: Int,
        isMovie: Boolean,
        getFromLocal: suspend () -> List<Person>,
        getFromRemote: suspend () -> CreditsResponse
    ): List<Person> {
        Log.d("FOLLOWERROR", "Hereeeeee 2")
        return getFromLocal().ifEmpty {

            Log.d("FOLLOWERROR", "Hereeeeee 3")
            val response = getFromRemote()
            val cast = response.cast.map { it.toEntity(PersonType.CAST) }
            val crew = response.crew.map { it.toEntity(PersonType.CREW) }
            val people = cast + crew

            Log.d("FOLLOWERROR", "Hereeeeee 4")
            if (isMovie) {
                Log.d("FOLLOWERROR", "Hereeeeee 5")
                localDataSource.insertPeopleForMovie(
                    people.map {
                        it.toCacheDto()
                    },
                    id
                )
            } else {
                localDataSource.insertPeopleForSeries(
                    people.map {
                        it.toCacheDto()
                    },
                    id
                )
            }
            Log.d("FOLLOWERROR", "Hereeeeee 11")
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