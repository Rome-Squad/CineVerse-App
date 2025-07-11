package com.giraffe.person.util

import com.giraffe.person.entity.Role

fun String.toRole(character: String? = null) = when (this) {
    "Actor" -> Role.Actor(character)
    "Director" -> Role.Director
    "ScreenPlay" -> Role.ScreenPlay
    "Story" -> Role.Story
    else -> throw IllegalArgumentException("Unknown role type: $this")
}

fun Role.toStr() = when (this) {
    is Role.Actor -> "Actor"
    is Role.Director -> "Director"
    is Role.ScreenPlay -> "ScreenPlay"
    is Role.Story -> "Story"
}
