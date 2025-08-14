package com.giraffe.media.explore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchKeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    @Query("SELECT * FROM SEARCH_KEYWORD ORDER BY searchedAt DESC")
    fun getSearchHistory(): Flow<List<SearchKeywordCacheDto>>

    @Query("SELECT * FROM SEARCH_KEYWORD WHERE keyword like '%' || :query || '%' ORDER BY searchedAt DESC")
    fun getSearchKeywords(query: String): Flow<List<SearchKeywordCacheDto>>

    @Query("SELECT * FROM SEARCH_KEYWORD WHERE keyword = :query ")
    fun getSearchKeyword(query: String): SearchKeywordCacheDto?

    @Query("DELETE FROM SEARCH_KEYWORD WHERE keyword = :keyword ")
    suspend fun deleteSearchKeyword(keyword: String)

    @Query("DELETE FROM SEARCH_KEYWORD")
    suspend fun clearSearchHistory()

    @Query(
        """
        Delete From SEARCH_KEYWORD
        WHERE searchedAt <= :currentTime - 900000
        """
    )
    suspend fun clearExpiredSearch(currentTime: Long)
}