package com.giraffe.match.screen.result

sealed interface MatchResultScreenEffect {
    data class ShowError(val error: Throwable) : MatchResultScreenEffect
    object NavigateBack : MatchResultScreenEffect
    object NavigateToLogin : MatchResultScreenEffect
    data class NavigateToMovieDetails(val movieId: Int) : MatchResultScreenEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : MatchResultScreenEffect
    data class NavigateToYouTubePlayer(val youtubeVideoId: String) : MatchResultScreenEffect
}