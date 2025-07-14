package com.giraffe.person.util

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.PersonType
import com.giraffe.person.local.dto.PersonDto
import com.giraffe.person.remote.response.CastResponse
import com.giraffe.person.remote.response.CrewResponse
import com.giraffe.person.remote.response.PersonResponse

fun Person.toDto(movieId: Int=-1, seriesId: Int=-1) = PersonDto(
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
fun CastResponse.toEntityForMovie(type: PersonType) = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = profilePath,
    type = type,
)

fun CrewResponse.toEntityForMovie(type: PersonType) = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath,
    type = type,
)
fun CastResponse.toEntityForShow(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = profilePath,
    type = type,
)

fun CrewResponse.toEntityForShow(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath,
    type = type,
)