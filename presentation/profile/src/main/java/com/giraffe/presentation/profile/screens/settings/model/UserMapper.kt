package com.giraffe.presentation.profile.screens.settings.model

import com.giraffe.user.entity.User

fun User.toUserUiModel() = UserUiModel(
    name = this.displayName.orEmpty(),
    username = this.username,
    imageUrl = this.avatarUrl.orEmpty()
)