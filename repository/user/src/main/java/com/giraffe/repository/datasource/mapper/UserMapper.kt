package com.giraffe.repository.datasource.mapper

import com.giraffe.repository.dto.UserDto
import com.giraffe.user.entity.User

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

fun UserDto.toEntity(): User {
    val fullAvatarUrl = this.avatar.tmdb.avatarPath?.let {
        BASE_IMAGE_URL + it
    }.orEmpty()
    return User(
        id = this.id,
        displayName = this.name.orEmpty(),
        username = this.username.orEmpty(),
        avatarUrl = fullAvatarUrl
    )
}