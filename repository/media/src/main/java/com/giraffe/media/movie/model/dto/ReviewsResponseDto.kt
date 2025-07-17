package com.giraffe.media.movie.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsResponseDto(
    val id: Int,
    val page: Int,
    val results: List<MovieReviewDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class MovieReviewDto(
    val id: String,
    val author: String,
    @SerialName("author_details") val authorDetails: AuthorDetailsDto,
    val content: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    val url: String
)

@Serializable
data class AuthorDetailsDto(
    val name: String?,
    val username: String,
    @SerialName("avatar_path") val avatarPath: String?,
    val rating: Int
)
