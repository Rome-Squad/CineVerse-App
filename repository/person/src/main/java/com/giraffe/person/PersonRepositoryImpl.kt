package com.giraffe.person

import com.giraffe.person.entity.Person
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

    override suspend fun getPeopleByMovieId(movieId: Int): List<Person> {
        return listOf(
            Person(
                id = 1,
                name = "Christian Bale",
                role = "Bruce Wayne / Batman",
                imageUrl = "https://image.tmdb.org/t/p/w500/profile/christian_bale.jpg"
            ),
            Person(
                id = 2,
                name = "Michael Caine",
                role = "Alfred Pennyworth",
                imageUrl = "https://image.tmdb.org/t/p/w500/profile/michael_caine.jpg"
            ),
            Person(
                id = 3,
                name = "Heath Ledger",
                role = "The Joker",
                imageUrl = "https://image.tmdb.org/t/p/w500/profile/heath_ledger.jpg"
            )
        )
    }
}