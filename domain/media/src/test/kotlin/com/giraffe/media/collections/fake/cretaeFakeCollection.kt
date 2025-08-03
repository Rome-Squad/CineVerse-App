package com.giraffe.media.collections.fake

import com.giraffe.media.collections.entity.CollectionType
import com.giraffe.media.collections.entity.Collection

fun createFakeCollection(
    id: Int = 1,
    name: String = "Favorites",
    description: String = "My favorite movies",
    type: CollectionType = CollectionType.MOVIE
): Collection {
    return Collection(
        id = id,
        name = name,
        description = description,
        type = type
    )
}
