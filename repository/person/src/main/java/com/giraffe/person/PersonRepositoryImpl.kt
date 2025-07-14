package com.giraffe.person

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.PersonType
import com.giraffe.person.local.PersonLocalDataSource
import com.giraffe.person.remote.PersonRemoteDataSource
import com.giraffe.person.repository.PersonRepository
import com.giraffe.person.util.SafeCall
import com.giraffe.person.util.toDto
import com.giraffe.person.util.toEntity
import com.giraffe.person.util.toEntityForMovie
import com.giraffe.person.util.toEntityForShow

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

    override suspend fun getPeopleByMovieId(movieId: Int) = SafeCall {
        val localPeople = localDataSource.getPeopleByMovieId(movieId).map { it.toEntity() }

        localPeople.ifEmpty {
            val response = remoteDataSource.getCreditsByMovieId(movieId)

            val cast = response.cast.map { it.toEntityForMovie(PersonType.CAST) }
            val crew = response.crew.map { it.toEntityForMovie(PersonType.CREW) }

            val people = cast + crew

            val peopleDto = people.map { it.toDto(movieId = movieId) }

            localDataSource.storePeople(peopleDto)

            people
        }
    }

    override suspend fun getPeopleByShowId(showId: Int) = SafeCall {
        val localPeople = localDataSource.getPeopleBySeriesId(showId).map { it.toEntity() }

        localPeople.ifEmpty {
            val response = remoteDataSource.getCreditsByShowId(showId)

            val cast = response.cast.map { it.toEntityForShow(PersonType.CAST) }
            val crew = response.crew.map { it.toEntityForShow(PersonType.CREW) }

            val people = cast + crew

            val peopleDto = people.map { it.toDto(showId = showId) }

            localDataSource.storePeople(peopleDto)

            people
        }
    }
}