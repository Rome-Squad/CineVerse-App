package com.giraffe.person.util

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.PersonType
import com.giraffe.person.local.dto.PersonDto
import com.giraffe.person.remote.response.CastMovieResponse
import com.giraffe.person.remote.response.CrewMovieResponse
import com.giraffe.person.remote.response.PersonResponse

fun Person.toDto() = PersonDto(
    id = id,
    name = name,
    imageUrl = imageUrl,
    role = role,
    type = type.name,
    movieId = movieId
)

fun PersonDto.toEntity(): Person = Person(
    id = id,
    name = name,
    imageUrl = imageUrl,
    role = role,
    type = type?.let { PersonType.valueOf(it) } ?: PersonType.CAST, movieId = movieId
)

fun PersonResponse.toEntity(type: PersonType = PersonType.CAST) = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = profilePath,
    type = type
)
fun CastMovieResponse.toEntity(type: PersonType, movieId: Int) = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = profilePath,
    type = type,
    movieId = movieId
)

fun CrewMovieResponse.toEntity(type: PersonType, movieId: Int) = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath,
    type = type,
    movieId = movieId
)