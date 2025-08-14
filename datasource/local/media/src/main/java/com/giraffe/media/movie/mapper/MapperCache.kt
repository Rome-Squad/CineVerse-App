package com.giraffe.media.movie.mapper

import com.giraffe.media.movie.datasource.local.cacheDto.MatchesYourVibeMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.PopularMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentReleasedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.RecentlyViewedMovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.UpcomingMovieCacheDto

fun MovieCacheDto.toPopularMovieCacheDto() = PopularMovieCacheDto(id = id)

fun MovieCacheDto.toRecentReleasedMovieCacheDto() = RecentReleasedMovieCacheDto(id = id)

fun MovieCacheDto.toUpcomingMovieCacheDto() = UpcomingMovieCacheDto(id = id)

fun MovieCacheDto.toMatchesYourVibeMovieCacheDto() = MatchesYourVibeMovieCacheDto(id = id)

fun MovieCacheDto.toRecentlyViewedMovieCacheDto() = RecentlyViewedMovieCacheDto(id = id)
