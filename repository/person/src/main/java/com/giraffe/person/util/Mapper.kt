package com.giraffe.person.util

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.PersonType
import com.giraffe.person.local.dto.PersonDto
import com.giraffe.person.remote.response.CastResponse
import com.giraffe.person.remote.response.CrewResponse
import com.giraffe.person.remote.response.PersonResponse

fun Person.toDto() = PersonDto(
    id = id,
    name = name,
    imageUrl = imageUrl,
    role = role,
    type = type.name,
    movieId = movieId,
    showId = showId
)

fun PersonDto.toEntity(): Person = Person(
    id = id,
    name = name,
    imageUrl = imageUrl,
    role = role,
    type = type.let { PersonType.valueOf(it) },
    movieId = movieId, showId = showId
)

fun PersonResponse.toEntity(type: PersonType = PersonType.CAST) = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = profilePath,
    type = type
)
fun CastResponse.toEntityForMovie(type: PersonType, movieId: Int) = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = profilePath,
    type = type,
    movieId = movieId
)

fun CrewResponse.toEntityForMovie(type: PersonType, movieId: Int) = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath,
    type = type,
    movieId = movieId
)
fun CastResponse.toEntityForShow(type: PersonType, showId: Int): Person = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = profilePath,
    type = type,
    movieId = -1,
    showId = showId
)

fun CrewResponse.toEntityForShow(type: PersonType, showId: Int): Person = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath,
    type = type,
    movieId = -1,
    showId = showId
)