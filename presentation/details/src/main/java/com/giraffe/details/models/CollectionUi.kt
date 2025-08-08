package com.giraffe.details.models

import com.giraffe.media.collections.entity.Collection

data class CollectionUi(
    val id: Int,
    val title: String,
    val isLoading: Boolean
)

fun Collection.toUi() = CollectionUi(
    id = id,
    title = name,
    isLoading = false
)