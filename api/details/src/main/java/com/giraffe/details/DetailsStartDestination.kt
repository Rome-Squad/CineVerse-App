package com.giraffe.details

sealed class DetailsStartDestination {
    data class Movie(val movieId: Int) : DetailsStartDestination()
    data class Series(val seriesId: Int) : DetailsStartDestination()
    data class Cast(val castId: Int) : DetailsStartDestination()
}