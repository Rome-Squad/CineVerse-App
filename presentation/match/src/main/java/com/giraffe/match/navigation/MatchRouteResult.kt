package com.giraffe.match.navigation

import kotlinx.serialization.Serializable


@Serializable
internal data class MatchRouteResult(
    val selectedGenres: List<Int>,
    val moodSelections: List<Int>,
    val timeSelection: Int? = null,
    val releasePeriodSelection: String? = null
)