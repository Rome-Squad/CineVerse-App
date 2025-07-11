package com.giraffe.person.util

import com.giraffe.person.entity.Person
import com.giraffe.person.local.dto.PersonDto
import com.giraffe.person.remote.response.PersonResponse

fun Person.toDto() = PersonDto(
    id = id,
    name = name,
    imageUrl = imageUrl,
    role = role,
)

fun PersonDto.toEntity() = Person(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    role = role
)

fun PersonResponse.toEntity() = Person(
    id = id,
    name = name,
    role = knownForDepartment,
    imageUrl = profilePath
)

fun PersonResponse.toDto() = PersonDto(
    id = id,
    name = name,
    role = knownForDepartment,
    imageUrl = profilePath
)

