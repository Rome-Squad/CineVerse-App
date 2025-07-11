package com.giraffe.person.entity

data class Person(
    val id: Int,
    val name: String,
    val role: Role,
    val imageUrl: String? = null
)

sealed class Role(
    val character: String? = null
) {
    class Actor(character: String?) : Role(character)
    object Director : Role()
    object ScreenPlay : Role()
    object Story : Role()
}
