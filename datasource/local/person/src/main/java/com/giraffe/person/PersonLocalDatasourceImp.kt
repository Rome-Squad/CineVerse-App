package com.giraffe.person

import com.giraffe.person.dao.PersonDao
import com.giraffe.person.local.PersonLocalDatasource
import com.giraffe.person.local.dto.PersonDto

class PersonLocalDatasourceImp(private val dao: PersonDao) : PersonLocalDatasource {
    override suspend fun storePerson(person: PersonDto) = dao.storePerson(person)
    override suspend fun searchByName(personName: String) = dao.searchByName(personName)
    override suspend fun getPersonById(id: Int) = dao.getPersonById(id)
    override suspend fun getPersonByName(personName: String) = dao.getPersonByName(personName)
    override suspend fun getRecentPeople() = dao.getRecentPeople()
    override suspend fun clearRecentPeople() = dao.clearRecentPeople()
}