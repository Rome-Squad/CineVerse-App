//package com.giraffe.media.utils
//
//import com.giraffe.media.mediaMember.entity.CastMember
//import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
//import com.giraffe.media.person.entity.Person
//import com.giraffe.media.person.mapper.toCacheDto
//import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
//import com.giraffe.media.person.datasource.remote.dto.CreditsDto
//
//sealed class ContentType {
//    abstract val id: Int
//    abstract suspend fun fetchCredits(): CreditsDto
//    abstract fun toCacheDto(cast: CastMember): PersonCacheDto
//
//    class Movie(
//        override val id: Int,
//        private val remote: MediaMemberRemoteDataSource
//    ) : ContentType() {
//        override suspend fun fetchCredits() = remote.getCreditsByMovieId(id)
//        override fun toCacheDto(person: Person) = person.toCacheDto()
//    }
//
//    class Series(
//        override val id: Int,
//        private val remote: MediaMemberRemoteDataSource
//    ) : ContentType() {
//        override suspend fun fetchCredits() = remote.getCreditsBySeriesId(id)
//        override fun toCacheDto(person: Person) = person.toCacheDto()
//    }
//}
