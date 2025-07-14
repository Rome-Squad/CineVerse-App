package com.giraffe.person

import com.giraffe.person.dao.PersonDao
import com.giraffe.person.local.PersonLocalDataSource
import com.giraffe.person.local.dto.PersonDto

class PersonLocalDataSourceImp(private val dao: PersonDao) : PersonLocalDataSource {
    override suspend fun storePerson(person: PersonDto) = dao.storePerson(person)
    override suspend fun searchByName(personName: String) = dao.searchByName(personName)
    override suspend fun getPersonById(id: Int) = dao.getPersonById(id)
    override suspend fun getPersonByName(personName: String) = dao.getPersonByName(personName)
    override suspend fun getRecentPeople() = dao.getRecentPeople()
    override suspend fun clearRecentPeople() = dao.clearRecentPeople()
    override suspend fun getPeopleByMovieId(movieId: Int): List<PersonDto> = dao.getPeopleByMovieId(movieId)
    override suspend fun storePeople(people: List<PersonDto>) = dao.insertPeople(people)
    override suspend fun getPeopleBySeriesId(seriesId: Int): List<PersonDto> = dao.getPeopleBySeriesId(seriesId)

}