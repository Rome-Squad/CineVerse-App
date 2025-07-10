package com.giraffe.person

import com.giraffe.person.entity.Person
import com.giraffe.person.local.PersonLocalDatasource
import com.giraffe.person.remote.PersonRemoteDataSource
import com.giraffe.person.repository.PersonRepository
import com.giraffe.person.util.safeCall
import com.giraffe.person.util.toDto
import com.giraffe.person.util.toEntity

class PersonRepositoryImpl(
    private val remoteDataSource: PersonRemoteDataSource,
    private val localDatasource: PersonLocalDatasource,
) : PersonRepository {
    override suspend fun searchByName(personName: String) = safeCall {
        localDatasource.searchByName(personName).map { it.toEntity() }.ifEmpty {
            remoteDataSource.searchByName(personName).people
                .map { it.toEntity() }
                .also { people ->
                    people.map { localDatasource.storePerson(it.toDto()) }
                }
        }
    }

    override suspend fun storeRecentPerson(person: Person) =
        safeCall { localDatasource.storePerson(person.toDto().copy(isRecent = true)) }

    override suspend fun getRecentPeople() = safeCall {
        localDatasource.getRecentPeople().map { it.toEntity() }
    }

    override suspend fun clearRecentPeople() = safeCall {
        localDatasource.clearRecentPeople()
    }
}