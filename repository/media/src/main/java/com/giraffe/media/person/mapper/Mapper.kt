package com.giraffe.media.person.mapper

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.model.cacheDto.PersonCacheDto
import com.giraffe.media.person.model.dto.CastDto
import com.giraffe.media.person.model.dto.CrewDto
import com.giraffe.media.person.model.dto.PersonDto
import com.giraffe.media.person.model.dto.PersonMovieCastItemDto
import com.giraffe.media.person.model.dto.PersonProfileImageDto
import com.giraffe.media.person.model.dto.PersonTvCastDto
import com.giraffe.media.utils.BASE_IMAGE_URL


fun PersonCacheDto.toEntity(type: PersonType = PersonType.CAST) = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = BASE_IMAGE_URL + imageUrl,
    type = type
)
fun Person.toDto() = PersonCacheDto(
    id = id,
    name = name,
    imageUrl = BASE_IMAGE_URL + imageUrl,
    role = role,
    type = type.name,
)

fun PersonDto.toEntity() = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = BASE_IMAGE_URL + profilePath,
)


fun CastDto.toEntity(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = BASE_IMAGE_URL + profilePath,
    type = type,
)

fun CrewDto.toEntity(type: PersonType): Person = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = BASE_IMAGE_URL + profilePath,
    type = type,
)

fun PersonProfileImageDto.toImageList(): List<String> {
    return profiles.map { it.filePath }
}

fun List<PersonMovieCastItemDto>.toEntity(): List<PersonCredit> {
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