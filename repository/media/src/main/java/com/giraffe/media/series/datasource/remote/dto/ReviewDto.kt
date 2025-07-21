package com.giraffe.media.series.datasource.remote.dto

import com.google.gson.annotations.SerializedName



data class ReviewDto(
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
    val name: String,
    val username: String,
    @SerializedName("avatar_path")
    val avatarPath: String,
    val rating: Int,
)