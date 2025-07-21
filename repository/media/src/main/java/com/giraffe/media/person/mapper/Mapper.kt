package com.giraffe.media.person.mapper

import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.remote.dto.CastDto
import com.giraffe.media.person.datasource.remote.dto.CrewDto
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.PersonDto
import com.giraffe.media.person.datasource.remote.dto.PersonProfileImageDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.entity.PersonSocialMediaLinks
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.utils.AT_SYMBOLS_URL
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.FACEBOOK_URL
import com.giraffe.media.utils.INSTAGRAM_URL
import com.giraffe.media.utils.YOUTUBE_URL


fun PersonCacheDto.toEntity(type: PersonType = PersonType.CAST): Person {
    return Person(
        id = id,
        name = name,
        role = role,
        imageUrl = imageUrl?.let {
            if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
        },
        type = type
    )
}

fun Person.toDto(): PersonCacheDto {
    return PersonCacheDto(
        id = id,
        name = name,
        imageUrl = imageUrl?.let {
            if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
        },
        role = role,
        type = type.name,
    )
}

fun PersonDto.toEntity(): Person {
    return Person(
        id = id,
        name = name,
        role = role,
        imageUrl = profilePath?.let {
            if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
        },
    )
}

fun CastDto.toEntity(type: PersonType) = Person(
    id = id,
    name = name,
    role = character,
    imageUrl = profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    type = type,
)

fun CrewDto.toEntity(type: PersonType) = Person(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    type = type,
)

fun PersonCreditDto.toEntity(): PersonCredit = PersonCredit(
    id = id,
    title = title.orEmpty(),
    posterPath = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    voteAverage = voteAverage,
    mediaType = mediaType
)

fun PersonSocialMediaDto.toEntity(): PersonSocialMediaLinks = PersonSocialMediaLinks(
    facebookLink = facebookId.prependIfNotBlank(FACEBOOK_URL),
    instagramLink = instagramId.prependIfNotBlank(INSTAGRAM_URL),
    youtubeLink = youtubeId.prependIfNotBlank(YOUTUBE_URL + AT_SYMBOLS_URL),
)

fun PersonProfileImageDto.toImageList(): List<String> =
    profiles.map { BASE_IMAGE_URL + it.filePath }

fun String?.prependIfNotBlank(prefix: String): String? =
    this?.takeIf { it.isNotBlank() }?.let { prefix + it }