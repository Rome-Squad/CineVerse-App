package com.giraffe.media.series.mapper

import com.giraffe.media.series.datasource.local.cacheDto.PopularSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.RecentlyReleasedSeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto
import com.giraffe.media.series.datasource.local.cacheDto.TopRatedSeriesCacheDto

fun SeriesCacheDto.toPopularSeriesCacheDto() = PopularSeriesCacheDto(
    id = id,
)

fun SeriesCacheDto.toRecentlyReleasedSeriesCacheDto() = RecentlyReleasedSeriesCacheDto(
    id = id,
)

fun SeriesCacheDto.toTopRatedSeriesCacheDto() = TopRatedSeriesCacheDto(
    id = id,
)