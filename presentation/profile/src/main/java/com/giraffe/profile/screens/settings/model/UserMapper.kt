package com.giraffe.profile.screens.settings.model

import com.giraffe.user.entity.User

fun User.toUserUiModel() = UserUiModel(
    name = this.displayName,
    username = this.username,
    imageUrl = this.avatarUrl ?: ""
)