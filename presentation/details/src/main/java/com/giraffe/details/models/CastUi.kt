package com.giraffe.details.models

import com.giraffe.media.person.entity.Person

data class CastUi(
    val name : String = "",
    val role : String = "",
    val imageUrl : String? = ""
)

fun Person.toCastUi() : CastUi{
    return CastUi(
        name = name,
        role = role,
        imageUrl = imageUrl
    )
}

