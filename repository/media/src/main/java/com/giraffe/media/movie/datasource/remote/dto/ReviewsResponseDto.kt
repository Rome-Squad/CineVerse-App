package com.giraffe.media.movie.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewsResponseDto(
    val id: Int,
    val page: Int,
    val results: List<MovieReviewDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class MovieReviewDto(
    val id: String,
    val author: String,
    @SerializedName("author_details")
    val authorDetails: AuthorDetailsDto,
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)

data class AuthorDetailsDto(
    val name: String?,
    val username: String,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    val rating: Int
)
