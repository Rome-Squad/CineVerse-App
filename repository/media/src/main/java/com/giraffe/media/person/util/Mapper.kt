package com.giraffe.media.person.util

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.remote.dto.CastDto
import com.giraffe.media.person.remote.dto.CrewDto
import com.giraffe.media.person.remote.dto.PersonDto
import com.giraffe.media.person.remote.dto.PersonMovieCastItemDto
import com.giraffe.media.person.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.remote.dto.PersonTvCastDto

private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500"
fun Person.toDto(movieId: Int = -1, seriesId: Int = -1) = PersonCacheDto(
    id = id,
    name = name,
    imageUrl = BASE_IMAGE_URL + imageUrl,
    role = role,
    type = type.name,
    movieId = movieId,
    seriesId = seriesId
)

fun PersonDto.toMovieCredits() = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = BASE_IMAGE_URL + profilePath,
)

fun PersonCacheDto.toMovieCredits(type: PersonType = PersonType.CAST) = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = BASE_IMAGE_URL + imageUrl,
    type = type
)

fun CastDto.toMovieCredits(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = BASE_IMAGE_URL + profilePath,
    type = type,
)

fun CrewDto.toMovieCredits(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = BASE_IMAGE_URL + profilePath,
    type = type,
)

fun PersonProfileImageDto.toImageList(): List<String> {
    return profiles.map { it.filePath }
}

fun List<PersonMovieCastItemDto>.toMovieCredits(): List<PersonCredit> {
    return map {
        PersonCredit(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
    }
}

fun List<PersonTvCastDto>.toTvCredits(): List<PersonCredit> {
    return map {
        PersonCredit(
            id = it.id,
            title = it.name,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
    }
}