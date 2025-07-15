package com.giraffe.media.person.util

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.local.dto.PersonDto
import com.giraffe.media.person.remote.response.CastResponse
import com.giraffe.media.person.remote.response.CrewResponse
import com.giraffe.media.person.remote.response.PersonMovieCastItemResponse
import com.giraffe.media.person.remote.response.PersonProfileImageResponse
import com.giraffe.media.person.remote.response.PersonResponse
import com.giraffe.media.person.remote.response.PersonTvCastItem

private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500"
fun Person.toDto(movieId: Int = -1, seriesId: Int = -1) = PersonDto(
    id = id,
    name = name,
    imageUrl = BASE_IMAGE_URL + imageUrl,
    role = role,
    type = type.name,
    movieId = movieId,
    seriesId = seriesId
)

fun PersonDto.toEntity(): Person = Person(
    id = id,
    name = name,
    imageUrl = BASE_IMAGE_URL + imageUrl,
    role = role,
    type = type.let { PersonType.valueOf(it) }
)

fun PersonResponse.toEntity(type: PersonType = PersonType.CAST) = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = BASE_IMAGE_URL + profilePath,
    type = type
)

fun CastResponse.toEntity(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = BASE_IMAGE_URL + profilePath,
    type = type,
)

fun CrewResponse.toEntity(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = BASE_IMAGE_URL + profilePath,
    type = type,
)

fun PersonProfileImageResponse.toImageList(): List<String> {
    return profiles.map { it.filePath }
}

fun List<PersonMovieCastItemResponse>.toPersonMovieCredits(): List<PersonCredit> {
    return map {
        PersonCredit(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
    }
}

fun List<PersonTvCastItem>.toPersonTvCredits(): List<PersonCredit> {
    return map {
        PersonCredit(
            id = it.id,
            title = it.name,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
    }
}