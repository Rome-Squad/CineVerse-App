package com.giraffe.series.model


data class SeriesFullData(
    val series: CachedSeriesDto,
    val seasons: List<CachedSeasonDto>,
    val genres: List<CachedSeriesGenreDto>
)
