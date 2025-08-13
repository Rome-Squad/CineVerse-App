package com.giraffe.media.series.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.floatOrNull

data class SeriesRateResponse(
    val id: Int,
    @SerialName("rated")
    val rated: JsonElement? = null
) {
    fun getRating(): Float? {
        return when (rated) {
            is JsonObject -> (rated["value"] as? JsonPrimitive)?.floatOrNull
            else -> null
        }
    }
}