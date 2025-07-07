import androidx.room.Dao
import androidx.room.Query
import com.giraffe.movie.dto.MovieDto
import com.giraffe.movie.utils.DatabaseConstants.MOVIE_TABLE

@Dao
interface MovieDao {
    @Query("SELECT * FROM $MOVIE_TABLE")
    fun getMovies(): List<MovieDto>
}