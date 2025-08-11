package com.giraffe.match.screen.match_pager

data class MatchScreenState(
    val currentPage: Int = 0,
    val moodSelections: List<Int> = emptyList(),
    val genreSelections: List<Int> = emptyList(),
    val timeSelections: List<Int> = emptyList(),
    val recencySelections: List<Int> = emptyList()
)