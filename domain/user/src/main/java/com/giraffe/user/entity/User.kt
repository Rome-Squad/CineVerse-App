package com.giraffe.user.entity

data class User(
    val id: Int,
    val displayName: String,
    val userName: String,
    val imageUrl: String,
    val email: String,
)
