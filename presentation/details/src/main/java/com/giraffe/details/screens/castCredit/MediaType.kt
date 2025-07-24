package com.giraffe.details.screens.castCredit

enum class MediaType(val value: String) {
    MOVIE("movie"),
    TV("tv");

    companion object {
        fun from(value: String?): MediaType? {
            return entries.find { it.value == value }
        }
    }
}