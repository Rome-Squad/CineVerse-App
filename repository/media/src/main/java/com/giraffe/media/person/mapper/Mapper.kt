package com.giraffe.media.person.mapper

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.model.cache.PersonCacheDto
import com.giraffe.media.person.model.dto.CastResponse
import com.giraffe.media.person.model.dto.CrewResponse
import com.giraffe.media.person.model.dto.PersonMovieCastItemResponse
import com.giraffe.media.person.model.dto.PersonProfileImageResponse
import com.giraffe.media.person.model.dto.PersonResponse
import com.giraffe.media.person.model.dto.PersonTvCastItem
import com.giraffe.media.utils.BASE_IMAGE_URL

fun Person.toCacheDto() = PersonCacheDto(
    id = id,
    name = name,
    imageUrl = BASE_IMAGE_URL + imageUrl,
    role = role,
    type = type.name
)

fun PersonCacheDto.toEntity(): Person = Person(
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