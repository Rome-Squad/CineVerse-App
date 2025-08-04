package com.giraffe.media.collections.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CollectionMediaTypeString = String?

@Serializable
data class CollectionDto(
    val id: Int = 0,
    val name: String,
    val description: String,
    @SerialName("item_count")
    val itemsCount: Int = 0,
    @SerialName("list_type")
    val type: CollectionMediaTypeString = "movie"

)