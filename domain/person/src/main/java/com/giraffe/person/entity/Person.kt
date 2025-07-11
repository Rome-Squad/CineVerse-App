package com.giraffe.person.entity

data class Person(
    val id: Int,
    val name: String,
    val role: String,
    val imageUrl: String? = null
)