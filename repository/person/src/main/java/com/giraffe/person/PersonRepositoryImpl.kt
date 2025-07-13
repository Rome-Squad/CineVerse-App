package com.giraffe.person

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.PersonType
import com.giraffe.person.local.PersonLocalDataSource
import com.giraffe.person.remote.PersonRemoteDataSource
import com.giraffe.person.repository.PersonRepository
import com.giraffe.person.util.SafeCall
import com.giraffe.person.util.toDto
import com.giraffe.person.util.toEntity

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

            val cast = response.cast.map { it.toEntity(PersonType.CAST, movieId) }
            val crew = response.crew.map { it.toEntity(PersonType.CREW, movieId) }

            val people = cast + crew

            people.forEach {
                localDataSource.storePerson(it.toDto())
            }
            people
        }
    }
}