package com.giraffe.media.home.repository

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series

interface HomeRepository {
    fun getPopularityMovies(): List<Movie>
    fun getPopularitySeries(): List<Series>
    fun getRecentlyReleasedMovies(): List<Movie>
    fun getRecentlyReleasedSeries(): List<Series>
    fun getTopRatedSeries(): List<Series>
    fun getUpcomingMovies(): List<Movie>
}