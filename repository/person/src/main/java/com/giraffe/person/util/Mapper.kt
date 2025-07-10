package com.giraffe.person.util

import com.giraffe.person.entity.Person
import com.giraffe.person.entity.PersonDto
import com.giraffe.person.response.PersonResponse

fun Person.toDto(): PersonDto {
    return PersonDto(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        role = role.toStr(),
        character = this.role.character
    )
}

fun PersonDto.toEntity(): Person {
    return Person(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        role = role.toRole()
    )
}

fun PersonResponse.toEntity() = Person(
    id = id,
    name = name,
    role = knownForDepartment.toRole(),
    imageUrl = profilePath
)

