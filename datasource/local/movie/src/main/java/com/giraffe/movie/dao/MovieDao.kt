import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.movie.datasource.local.cacheDto.MovieCacheDto.Companion.MOVIE_TABLE
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.movie.datasource.local.cacheDto.MovieGenreCacheDto.Companion.MOVIE_GENRE_TABLE


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieCacheDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(movies: List<MovieGenreCacheDto>)

    @Query("SELECT * FROM $MOVIE_TABLE WHERE title MATCH :movieName")
    suspend fun getMovieByName (movieName : String) : List<MovieCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE id =:movieId")
    suspend fun getMovieById (movieId : Int) : MovieCacheDto

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE WHERE id IN (:ids)")
    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE WHERE ID =:id")
    suspend fun getMovieGenreById(id : Int) : MovieGenreCacheDto

    @Query("SELECT * FROM $MOVIE_GENRE_TABLE")
    suspend fun getMovieGenres() : List<MovieGenreCacheDto>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE genresID =:genreId")
    suspend fun getMoviesByGenre(genreId : Int) : List<MovieCacheDto>

    @Query("DELETE FROM $MOVIE_TABLE")
    suspend fun clearMovieCache()

    @Query("DELETE FROM $MOVIE_GENRE_TABLE")
    suspend fun clearMovieGenreCache()
}