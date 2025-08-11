package com.giraffe.media.explore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchKeywordDao {

    @Query(
        value =
            "SELECT * " +
                    "FROM SEARCH_KEYWORD " +
                    "ORDER BY searchedAt DESC"
    )
    fun getSearchHistory(): Flow<List<SearchKeywordCacheDto>>

    @Query(
        value =
            "SELECT * " +
                    "FROM SEARCH_KEYWORD " +
                    "WHERE keyword like '%' || :query || '%' " +
                    "ORDER BY searchedAt DESC"
    )
    fun getSearchKeywords(query: String): Flow<List<SearchKeywordCacheDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    @Query(
        value =
            "DELETE " +
                    "FROM SEARCH_KEYWORD " +
                    "WHERE keyword = :keyword "
    )
    suspend fun deleteSearchKeyword(keyword: String)

    @Query("DELETE FROM SEARCH_KEYWORD")
    suspend fun clearSearchHistory()

    @Query(
        value =
            "SELECT * " +
                    "FROM SEARCH_KEYWORD " +
                    "WHERE keyword = :query "
    )
    fun getSearchKeyword(query: String): SearchKeywordCacheDto?

    @Query(
        """
    UPDATE SEARCH_KEYWORD
    SET 
        moviesPages = :emptyList,
        seriesPages = :emptyList,
        actorsPages = :emptyList
    WHERE searchedAt <= :currentTime - 3600000
    """
    )
    suspend fun clearExpiredKeywordPagesCache(currentTime: Long, emptyList: List<Int> = emptyList())
}