package com.giraffe.media.person.mapper

import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.remote.dto.CastDto
import com.giraffe.media.person.datasource.remote.dto.CrewDto
import com.giraffe.media.person.datasource.remote.dto.PersonCreditDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto
import com.giraffe.media.person.datasource.remote.dto.ProfileDto
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.entity.PersonSocialMediaLinks
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.utils.AT_SYMBOLS_URL
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.FACEBOOK_URL
import com.giraffe.media.utils.INSTAGRAM_URL
import com.giraffe.media.utils.TIKTOK_URL
import com.giraffe.media.utils.X_URL
import com.giraffe.media.utils.YOUTUBE_URL
import com.giraffe.media.utils.toFormattedDate


fun mapToPerson(
    personId: Int,
    details: PersonDetailsDto,
    images: List<ProfileDto>,
    media: List<PersonCreditDto>,
    socialMedia: PersonSocialMediaDto
): Person = Person(
    id = personId,
    name = details.name,
    imageUrl = details.profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    role = details.knownForDepartment,
    birthday = details.birthday,
    placeOfBirth = details.placeOfBirth,
    biography = details.biography,
    images = images.map(ProfileDto::toImageUrl),
    personCredits = media.map(PersonCreditDto::toEntity),
    socialMedia = socialMedia.toEntity()
)

fun PersonCacheDto.toEntity(type: PersonType = PersonType.CAST) = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = imageUrl?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    type = type
)

fun Person.toCacheDto() = PersonCacheDto(
    id = id,
    name = name,
    imageUrl = imageUrl?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    role = role,
    type = type.name,
)

fun PersonDto.toEntity() = Person(
    id = id,
    name = name,
    role = role,
    imageUrl = profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
)

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

fun PersonCreditDto.toEntity() = PersonCredit(
    id = id,
    title = title.orEmpty(),
    posterPath = posterPath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    voteAverage = voteAverage,
    mediaType = mediaType,
    genreIds = genreIds,
    releaseYear = if (releaseDate.isNullOrEmpty()) null else releaseDate.toFormattedDate(),
)

private fun PersonSocialMediaDto.toEntity(): PersonSocialMediaLinks = PersonSocialMediaLinks(
    facebookLink = facebookId.prependIfNotBlank(FACEBOOK_URL),
    instagramLink = instagramId.prependIfNotBlank(INSTAGRAM_URL),
    youtubeLink = youtubeId.prependIfNotBlank(YOUTUBE_URL),
    tiktokLink = tiktokId.prependIfNotBlank(TIKTOK_URL + AT_SYMBOLS_URL),
    twitterLink = twitterId.prependIfNotBlank(X_URL),
)

fun ProfileDto.toImageUrl(): String = BASE_IMAGE_URL + filePath

private fun String?.prependIfNotBlank(prefix: String): String? = if (!this.isNullOrBlank()) {
    prefix + this
} else null