package com.giraffe.media.person

import com.giraffe.media.person.dao.PersonDao
import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.person.model.cacheDto.PersonCacheDto

class PersonLocalDataSourceImp(private val dao: PersonDao) : PersonLocalDataSource {
    override suspend fun storePerson(person: PersonCacheDto) = dao.storePerson(person)
    override suspend fun searchByName(personName: String) = dao.searchByName(personName)
    override suspend fun getPersonById(id: Int) = dao.getPersonById(id)
    override suspend fun getPersonByName(personName: String) = dao.getPersonByName(personName)
    override suspend fun getRecentPeople() = dao.getRecentPeople()
    override suspend fun clearRecentPeople() = dao.clearRecentPeople()
    override suspend fun getPeopleByMovieId(movieId: Int): List<PersonCacheDto> = dao.getPeopleByMovieId(movieId)
    override suspend fun storePeople(people: List<PersonCacheDto>) = dao.insertPeople(people)
    override suspend fun getPeopleBySeriesId(seriesId: Int): List<PersonCacheDto> = dao.getPeopleBySeriesId(seriesId)

}