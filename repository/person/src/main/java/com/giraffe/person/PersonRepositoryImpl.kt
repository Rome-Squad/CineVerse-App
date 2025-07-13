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
            remoteDataSource.searchByName(personName).people
                .map { it.toEntity() }
                .also { people ->
                    people.map { localDataSource.storePerson(it.toDto()) }
                }
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

            val cast = response.cast.map { it.toEntityForMovie(PersonType.CAST, movieId) }
            val crew = response.crew.map { it.toEntityForMovie(PersonType.CREW, movieId) }

            val people = cast + crew

            people.forEach {
                localDataSource.storePerson(it.toDto())
            }
            people
        }
    }

    override suspend fun getPeopleByShowId(showId: Int) = SafeCall {
        val localPeopdle = localDataSource.getPeopleByShowId(showId)
        println("📦 Local people size for showId $showId: ${localPeopdle.size}")

        val localPeople = localDataSource.getPeopleByShowId(showId).map { it.toEntity() }

        localPeople.ifEmpty {
            val response = remoteDataSource.getCreditsByShowId(showId)

            val cast = response.cast.map { it.toEntityForShow(PersonType.CAST, showId = showId) }
            val crew = response.crew.map { it.toEntityForShow(PersonType.CREW, showId = showId) }

            val people = cast + crew

            people.forEach {
                localDataSource.storePerson(it.toDto())
            }
            people
        }
    }

}