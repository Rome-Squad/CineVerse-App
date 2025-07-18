package com.giraffe.media.utils

import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.mapper.toDto
import com.giraffe.media.person.model.cacheDto.PersonCacheDto
import com.giraffe.media.person.model.dto.CreditsDto

sealed class ContentType {
    abstract val id: Int
    abstract suspend fun fetchCredits(): CreditsDto
    abstract fun toDto(person: Person): PersonCacheDto

    class Movie(
        override val id: Int,
        private val remote: PersonRemoteDataSource
    ) : ContentType() {
        override suspend fun fetchCredits() = remote.getCreditsByMovieId(id)
        override fun toDto(person: Person) = person.toDto()
    }

    class Series(
        override val id: Int,
        private val remote: PersonRemoteDataSource
    ) : ContentType() {
        override suspend fun fetchCredits() = remote.getCreditsBySeriesId(id)
        override fun toDto(person: Person) = person.toDto()
    }
}
