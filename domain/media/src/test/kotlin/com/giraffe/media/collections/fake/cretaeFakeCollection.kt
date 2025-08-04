package com.giraffe.media.collections.fake

import com.giraffe.media.collections.entity.CollectionMediaType
import com.giraffe.media.collections.entity.Collection

fun createFakeCollection(
    id: Int = 1,
    name: String = "Favorites",
    description: String = "My favorite movies",
    type: CollectionMediaType = CollectionMediaType.MOVIE
): Collection {
    return Collection(
        id = id,
        name = name,
        description = description,
        type = type
    )
}
