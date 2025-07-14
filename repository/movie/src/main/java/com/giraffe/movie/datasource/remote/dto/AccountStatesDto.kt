package com.giraffe.movie.datasource.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AccountStatesDto(
    val id: Int,
    val favorite: Boolean,
    val rated: JsonElement
)

@Serializable
data class RatedDto(
    val value: Int
)