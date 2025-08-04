package com.giraffe.media.collections.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionItemDto(
    val id: Int,
    val title: String? = null,
    @SerialName("overview")
    val description: String? = null,
    @SerialName("vote_average")
    val rating: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("media_type")
    val type: CollectionMediaTypeString
)
