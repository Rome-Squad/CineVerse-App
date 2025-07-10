package com.giraffe.person

import android.Manifest
import androidx.annotation.RequiresPermission
import com.giraffe.person.entity.Person
import com.giraffe.person.repository.PersonRepository
import com.giraffe.person.util.ConnectionChecker
import com.giraffe.person.util.safeCall
import com.giraffe.person.util.toDto
import com.giraffe.person.util.toEntity

class PersonRepositoryImpl(
    private val remoteDataSource: PersonRemoteDataSource,
    private val localDatasource: PersonLocalDatasource,
    private val connectionChecker: ConnectionChecker
) : PersonRepository {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun searchByName(personName: String) = safeCall {
        if (connectionChecker.isConnected()) {
            remoteDataSource.searchByName(personName).people.map { it.toEntity() }
        } else {
            localDatasource.searchByName(personName).map { it.toEntity() }
        }
    }

    override suspend fun storePerson(person: Person) =
        safeCall { localDatasource.storePerson(person.toDto()) }

    override suspend fun getPersonById(id: Int) =
        safeCall { localDatasource.getPersonById(id).toEntity() }

    override suspend fun getPersonByName(personName: String) =
        safeCall { localDatasource.getPersonByName(personName).toEntity() }
}