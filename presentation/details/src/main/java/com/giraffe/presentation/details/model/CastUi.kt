package com.giraffe.presentation.details.model

import com.giraffe.media.person.entity.Person

data class CastUi(
    val id: Int = 0,
    val name : String = "",
    val role : String = "",
    val urlImage : String? = ""
)

fun Person.toCastUi() : CastUi{
    return CastUi(
        id = id,
        name = name,
        role = role,
        urlImage = imageUrl
    )
}

