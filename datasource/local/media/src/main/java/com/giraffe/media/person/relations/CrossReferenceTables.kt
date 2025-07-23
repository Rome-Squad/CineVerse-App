package com.giraffe.media.person.relations


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.relations.CrossReferenceConstants.MOVIE_PERSON_CROSS_REF_TABLE
import com.giraffe.media.person.relations.CrossReferenceConstants.SERIES_PERSON_CROSS_REF_TABLE
import com.giraffe.media.series.datasource.local.cacheDto.SeriesCacheDto

@Entity(
    tableName = MOVIE_PERSON_CROSS_REF_TABLE,
    primaryKeys = ["movieId", "personId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieCacheDto::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonCacheDto::class,
            parentColumns = ["id"],
            childColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("movieId"), Index("personId")]
)

data class MoviePersonCrossRef(
    val movieId: Int,
    val personId: Int
)

@Entity(
    tableName = SERIES_PERSON_CROSS_REF_TABLE,
    primaryKeys = ["seriesId", "personId"],
    foreignKeys = [
        ForeignKey(
            entity = SeriesCacheDto::class,
            parentColumns = ["id"],
            childColumns = ["seriesId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonCacheDto::class,
            parentColumns = ["id"],
            childColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("seriesId"), Index("personId")]
)
data class SeriesPersonCrossRef(
    val seriesId: Int,
    val personId: Int
)