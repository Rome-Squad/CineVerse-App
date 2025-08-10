package com.giraffe.details.models

import com.giraffe.media.mediaMember.entity.CastMember

data class CastUi(
    val id: Int = 0,
    val name : String = "",
    val role : String = "",
    val urlImage : String? = ""
)

fun CastMember.toCastUi(): CastUi {
    return CastUi(
        id = id,
        name = name,
        role = role,
        urlImage = imageUrl
    )
}

