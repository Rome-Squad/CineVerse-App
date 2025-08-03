package com.giraffe.media.collections.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CollectionTypeString = String?

@Serializable
data class CollectionDto(
    val id: Int,
    val name: String,
    val description: String,
    @SerialName("list_type")
    val type: CollectionTypeString = "movie"

)