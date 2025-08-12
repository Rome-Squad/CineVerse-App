package com.giraffe.match.screen.match_pager

sealed interface MatchScreenEffect {
    object NavigateBack : MatchScreenEffect
    object FinishMatching : MatchScreenEffect
    data class ShowError(val error: Throwable) : MatchScreenEffect

}