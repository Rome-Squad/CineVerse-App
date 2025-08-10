package com.giraffe.presentation.details.model

import com.giraffe.media.mediaMember.entity.CrewMember

data class CrewUi(
    val name: String = "",
    val role: String = "",
)

fun CrewMember.toCrewUi(): CrewUi {
    return CrewUi(
        name = name,
        role = role
    )
}

fun List<CrewUi>.groupByRole(): Map<String, List<String>> {
    return this.groupBy { it.role }.mapValues { it.value.map { member -> member.name } }
}
