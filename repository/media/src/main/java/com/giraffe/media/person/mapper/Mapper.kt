package com.giraffe.media.person.mapper

import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.entity.CrewMember
import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheType
import com.giraffe.media.person.datasource.remote.dto.CastDto
import com.giraffe.media.person.datasource.remote.dto.CrewDto
import com.giraffe.media.person.datasource.remote.dto.MediaMemberDto
import com.giraffe.media.person.datasource.remote.dto.PersonDetailsDto
import com.giraffe.media.person.datasource.remote.dto.PersonSocialMediaDto
import com.giraffe.media.person.datasource.remote.dto.ProfileDto
import com.giraffe.media.utils.AT_SYMBOLS_URL
import com.giraffe.media.utils.BASE_IMAGE_URL
import com.giraffe.media.utils.FACEBOOK_URL
import com.giraffe.media.utils.INSTAGRAM_URL
import com.giraffe.media.utils.TIKTOK_URL
import com.giraffe.media.utils.X_URL
import com.giraffe.media.utils.YOUTUBE_URL

fun mapToCast(
    personId: Int,
    details: PersonDetailsDto,
    images: List<ProfileDto>,
    socialMedia: PersonSocialMediaDto
): CastMember = CastMember(
    id = personId,
    name = details.name,
    imageUrl = details.profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    role = details.department,
    birthday = details.birthday,
    placeOfBirth = details.placeOfBirth,
    biography = details.biography,
    otherImages = images.map(ProfileDto::toImageUrl),
    socialMedia = socialMedia.toEntity(),
    characterName = null
)

fun mapToCrew(
    personId: Int,
    details: PersonDetailsDto,
    images: List<ProfileDto>,
    socialMedia: PersonSocialMediaDto
): CrewMember = CrewMember(
    id = personId,
    name = details.name,
    imageUrl = details.profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    role = details.department,
    birthday = details.birthday,
    placeOfBirth = details.placeOfBirth,
    biography = details.biography,
    otherImages = images.map(ProfileDto::toImageUrl),
    socialMedia = socialMedia.toEntity()
)

fun MediaMemberDto.toCastEntity() = CastMember(
    id = id,
    name = name,
    role = department,
    imageUrl = profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    biography = null,
    birthday = null,
    characterName = null,
    otherImages = null,
    placeOfBirth = null,
    socialMedia = null
)

fun MediaMemberDto.toCrewEntity() = CrewMember(
    id = id,
    name = name,
    role = department,
    imageUrl = profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    biography = null,
    birthday = null,
    otherImages = null,
    placeOfBirth = null,
    socialMedia = null
)

fun CastMember.toCacheDto() = PersonCacheDto(
    id = id,
    name = name,
    imageUrl = imageUrl?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    role = role,
    type = PersonCacheType.CAST.name
)

fun CrewMember.toCacheDto() = PersonCacheDto(
    id = id,
    name = name,
    imageUrl = imageUrl?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    role = role,
    type = PersonCacheType.CREW.name
)

fun PersonCacheDto.toCastMemberEntity() = CastMember(
    id = id,
    name = name,
    role = role,
    imageUrl = imageUrl?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    biography = null,
    birthday = null,
    characterName = null,
    otherImages = null,
    placeOfBirth = null,
    socialMedia = null,
)

fun PersonCacheDto.toCrewMemberEntity() = CrewMember(
    id = id,
    name = name,
    role = role,
    imageUrl = imageUrl?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    biography = null,
    birthday = null,
    otherImages = null,
    placeOfBirth = null,
    socialMedia = null,
)

fun CastDto.toEntity() = CastMember(
    id = id,
    name = name,
    characterName = character,
    imageUrl = profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    role = role,
    biography = null,
    birthday = null,
    otherImages = null,
    placeOfBirth = null,
    socialMedia = null,
)

fun CrewDto.toEntity() = CrewMember(
    id = id,
    name = name,
    role = job,
    imageUrl = profilePath?.let {
        if (it.contains(BASE_IMAGE_URL)) it else BASE_IMAGE_URL + it
    },
    biography = null,
    birthday = null,
    otherImages = null,
    placeOfBirth = null,
    socialMedia = null,
)

fun mapToMediaMembers(
    cast: List<PersonCacheDto>,
    crew: List<PersonCacheDto>
) = MediaMemberRepository.MediaMembers(
    cast = cast
        .orEmpty()
        .map(PersonCacheDto::toCastMemberEntity),

    crew = crew
        .orEmpty()
        .map(PersonCacheDto::toCrewMemberEntity)
)

fun ProfileDto.toImageUrl(): String = BASE_IMAGE_URL + filePath

private fun PersonSocialMediaDto.toEntity(): SocialMediaLinks = SocialMediaLinks(
    facebookLink = facebookId.prependIfNotBlank(FACEBOOK_URL),
    instagramLink = instagramId.prependIfNotBlank(INSTAGRAM_URL),
    youtubeLink = youtubeId.prependIfNotBlank(YOUTUBE_URL),
    tiktokLink = tiktokId.prependIfNotBlank(TIKTOK_URL + AT_SYMBOLS_URL),
    twitterLink = twitterId.prependIfNotBlank(X_URL),
)

private fun String?.prependIfNotBlank(prefix: String): String? = if (!this.isNullOrBlank()) {
    prefix + this
} else null