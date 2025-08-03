package com.giraffe.media.collections.datasource.remote.dto

typealias CollectionTypeString = String

data class CollectionDto(
    val id: Int,
    val name: String,
    val description: String,
    val type: CollectionTypeString = "movie"

)