package com.giraffe.details.screens.moviedetails.model

import com.giraffe.media.person.entity.Person

data class CrewUi(
    val name : String = "",
    val role : String = "",
)

fun Person.toCrewUi() : CrewUi{
    return CrewUi(
        name = name,
        role = role
    )
}

fun List<CrewUi>.groupByRole(): Map<String, List<String>> {
    return this.groupBy { it.role }.mapValues { it.value.map { member -> member.name } }
}
