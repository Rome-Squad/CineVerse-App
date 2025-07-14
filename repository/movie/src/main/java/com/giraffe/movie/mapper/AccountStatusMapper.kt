package com.giraffe.movie.mapper

import com.giraffe.movie.datasource.remote.dto.AccountStatesDto
import com.giraffe.movie.datasource.remote.dto.RatedDto
import com.giraffe.movies.entity.AccountStates
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

fun AccountStatesDto.toEntity(json: Json): AccountStates {
    val ratedValue = if (this.rated.jsonObject.isEmpty()) {
        null
    } else {
        json.decodeFromJsonElement(RatedDto.serializer(), this.rated).value
    }

    return AccountStates(
        id = this.id,
        isFavorite = this.favorite,
        ratedValue = ratedValue
    )
}