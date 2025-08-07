package com.giraffe.profile.model

import com.giraffe.media.collections.entity.Collection

data class CollectionUi(
    val id: Int = 0,
    val name: String = "",
    val itemCount: Int = 0,
    val description: String = "",
)

fun Collection.toUi() = CollectionUi(
    id = id,
    name = name,
    itemCount = itemsCount,
    description = description
)

fun CollectionUi.toEntity() = Collection(
    id = id,
    name = name,
    itemsCount = itemCount,
    description = description
)
