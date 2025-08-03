package com.giraffe.media.person.datasource.local

import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto


interface PersonLocalDataSource {
    suspend fun insertPerson(person: PersonCacheDto)
    suspend fun searchByName(personName: String, page: Int): List<PersonCacheDto>
    suspend fun getPersonById(id: Int): PersonCacheDto
    suspend fun getPersonByName(personName: String): PersonCacheDto
    suspend fun getRecentPeople(): List<PersonCacheDto>
    suspend fun clearRecentPeople()
    suspend fun getPeopleBySeriesId(seriesId: Int): List<PersonCacheDto>
    suspend fun getPeopleByMovieId(movieId: Int): List<PersonCacheDto>
    suspend fun insertPeople(people: List<PersonCacheDto>)
}