package com.giraffe.media.person.util

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.PersonCredit
import com.giraffe.person.entity.PersonType
import com.giraffe.person.local.dto.PersonDto
import com.giraffe.person.remote.response.CastResponse
import com.giraffe.person.remote.response.CrewResponse
import com.giraffe.person.remote.response.PersonMovieCastItemResponse
import com.giraffe.person.remote.response.PersonProfileImageResponse
import com.giraffe.person.remote.response.PersonResponse
import com.giraffe.person.remote.response.PersonTvCastItem

fun Person.toDto(movieId: Int = -1, seriesId: Int = -1) = PersonDto(
    id = id,
    name = name,
    imageUrl = imageUrl,
    role = role,
    type = type.name,
    movieId = movieId,
    seriesId = seriesId
)

fun PersonDto.toEntity(): Person = Person(
    id = id,
    name = name,
    imageUrl = imageUrl,
    role = role,
    type = type.let { PersonType.valueOf(it) }
)

fun PersonResponse.toEntity(type: PersonType = PersonType.CAST) = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = profilePath,
    type = type
)

fun CastResponse.toEntity(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = profilePath,
    type = type,
)

fun CrewResponse.toEntity(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath,
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