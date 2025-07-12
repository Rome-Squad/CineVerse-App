package com.giraffe.explore.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.explore.model.SearchKeywordCacheDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExploreSearchKeywordDao {

    @Query(
        value =
            "SELECT * " +
            "FROM SEARCH_KEYWORD_TABLE " +
            "ORDER BY lastSearchedTime DESC"
    )
    fun getSearchHistory(): Flow<List<SearchKeywordCacheDto>>
    @Query(
        value =
            "SELECT * " +
            "FROM SEARCH_KEYWORD_TABLE " +
            "WHERE keyword = :query " +
            "ORDER BY lastSearchedTime DESC"
    )
    fun getSearchKeywords(query: String): Flow<List<SearchKeywordCacheDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    @Delete
    suspend fun deleteSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    @Query("DELETE FROM SEARCH_KEYWORD_TABLE")
    suspend fun clearSearchHistory()

}