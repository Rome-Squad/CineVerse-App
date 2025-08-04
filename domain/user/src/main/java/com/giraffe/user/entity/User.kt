package com.giraffe.user.entity

data class User(
    val id: Int,
    val displayName: String,
    val username: String,
    val avatarUrl: String?
)
