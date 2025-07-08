package com.giraffe.series.dto


data class SeriesFullData(
    val series: SeriesEntity,
    val seasons: List<SeasonEntity>,
    val genres: List<SeriesGenreEntity>
)
