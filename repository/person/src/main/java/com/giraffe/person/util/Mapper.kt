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
    type = type.name
)

fun PersonDto.toEntity() = Person(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    role = this.role,
    type = PersonType.valueOf(type)
)

fun PersonResponse.toEntity(type: PersonType = PersonType.CAST) = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = profilePath,
    type = type
)

fun PersonResponse.toDto(type: PersonType) = PersonDto(
    id = id,
    name = name,
    role = role,
    imageUrl = profilePath,
    type = type.name
)
fun CastMovieResponse.toEntity(type: PersonType) = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = profilePath,
    type = type
)

fun CrewMovieResponse.toEntity(type: PersonType) = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath,
    type = type
)