package com.giraffe.repository.datasource.mapper

import com.giraffe.repository.dto.AccountDetailsDto
import com.giraffe.user.entity.AccountDetails

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

fun AccountDetailsDto.toEntity(): AccountDetails {
    val fullAvatarUrl = this.avatar.tmdb.avatarPath?.let {
        BASE_IMAGE_URL + it
    }
    return AccountDetails(
        id = this.id,
        displayName = this.name,
        username = this.username,
        avatarUrl = fullAvatarUrl
    )
}