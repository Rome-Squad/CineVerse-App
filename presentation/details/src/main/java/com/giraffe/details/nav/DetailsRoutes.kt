package com.giraffe.details.nav

import com.giraffe.details.models.ReviewUI
import kotlinx.serialization.Serializable

internal sealed class DetailsRoutes

@Serializable
internal data class MovieDetailsRoute(val id: Int) : DetailsRoutes()

@Serializable
internal data class SeriesDetailsRoute(val id: Int) : DetailsRoutes()

@Serializable
internal data class CastDetailsRoute(val id: Int) : DetailsRoutes()

@Serializable
internal data class ReviewRoute(val reviews: List<ReviewUI>?) : DetailsRoutes()