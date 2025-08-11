package com.giraffe.media.person

import com.giraffe.media.person.dao.PersonDao
import com.giraffe.media.person.datasource.local.MediaMemberLocalDataSource
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.util.safeCall
import javax.inject.Inject

class MediaMemberLocalDataSourceImp @Inject constructor(private val dao: PersonDao) :
    MediaMemberLocalDataSource {
    override suspend fun insertPerson(person: PersonCacheDto) = safeCall {
        dao.insertPerson(person)
    }

    override suspend fun searchByName(personName: String, page: Int) = safeCall {
        dao.searchByName(personName, page)
    }

    override suspend fun getPersonById(id: Int) = safeCall {
        dao.getPersonById(id)
    }

    override suspend fun getPersonByName(personName: String) = safeCall {
        dao.getPersonByName(personName)
    }

    override suspend fun getRecentPeople() = safeCall {
        dao.getRecentPeople()
    }

    override suspend fun clearRecentPeople() = safeCall {
        dao.clearRecentPeople()
    }

    override suspend fun getPeopleByMovieId(movieId: Int) = safeCall {
        dao.getPeopleByMovieId(movieId)
    }

    override suspend fun insertPeople(people: List<PersonCacheDto>) = safeCall {
        dao.insertPeople(people)
    }

    override suspend fun getPeopleBySeriesId(seriesId: Int) = safeCall {
        dao.getPeopleBySeriesId(seriesId)
    }
}