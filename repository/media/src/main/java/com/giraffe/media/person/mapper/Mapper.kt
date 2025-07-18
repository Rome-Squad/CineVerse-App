package com.giraffe.media.person.mapper

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.entity.PersonSocialMediaLinks
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.model.cacheDto.PersonCacheDto
import com.giraffe.media.person.model.dto.CastDto
import com.giraffe.media.person.model.dto.CrewDto
import com.giraffe.media.person.model.dto.PersonCreditDto
import com.giraffe.media.person.model.dto.PersonDto
import com.giraffe.media.person.model.dto.PersonProfileImageDto
import com.giraffe.media.person.model.dto.PersonSocialMediaDto
import com.giraffe.media.utils.AT_SYMBOLS_URL
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.FACEBOOK_URL
import com.giraffe.media.utils.INSTAGRAM_URL
import com.giraffe.media.utils.YOUTUBE_URL


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

fun PersonCreditDto.toEntity(): PersonCredit = PersonCredit(
    id = id,
    title = title.orEmpty(),
    posterPath = BASE_IMAGE_URL + posterPath,
    voteAverage = voteAverage
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