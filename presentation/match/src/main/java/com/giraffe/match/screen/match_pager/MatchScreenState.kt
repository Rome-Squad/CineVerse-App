package com.giraffe.match.screen.match_pager

data class MatchScreenState(
    val currentPage: Int = 0,
    val moodSelections: List<Int> = emptyList(),
    val genreSelections: List<Int> = emptyList(),
    val genreOptions: List<SelectionOption> = emptyList(),
    val timeSelection: Int? = null,
    val releasePeriodSelection: Int? = null,
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
)