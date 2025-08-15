package com.giraffe.media.collections.util

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.entity.CollectionMediaType

fun createFakeCollection(
    id: Int = 1,
    name: String = "Favorites",
    description: String = "My favorite movies",
    itemsCount: Int = 0,
    type: CollectionMediaType = CollectionMediaType.MOVIE
): Collection {
    return Collection(
        id = id,
        name = name,
        description = description,
        itemsCount = itemsCount,
        type = type
    )
}
