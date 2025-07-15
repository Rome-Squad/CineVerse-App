package  com.giraffe.media.movie.dao

import androidx.room.Dao
import androidx.room.Query
import  com.giraffe.media.movie.datasource.local.cacheDto.SearchKeywordCacheDto

@Dao
interface MoviesSearchHistoryDao {
    @Query(
        value =
            "SELECT * " +
            "FROM SEARCH_KEYWORD_TABLE " +
            "ORDER BY lastSearchedTime DESC"
    )
    suspend fun getSearchKeywords(): List<SearchKeywordCacheDto>
}