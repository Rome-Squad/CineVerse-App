package com.giraffe.user.entity

data class AccountDetails(
    val id: Int,
    val displayName: String,
    val username: String,
    val avatarUrl: String?
)
