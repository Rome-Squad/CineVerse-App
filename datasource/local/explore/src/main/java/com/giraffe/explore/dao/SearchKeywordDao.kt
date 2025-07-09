package com.giraffe.explore.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giraffe.explore.model.SearchKeywordCacheDto

@Dao
interface SearchKeywordDao {
    @Query(
        value =
            "SELECT * " +
            "FROM SEARCH_KEYWORD_TABLE " +
            "WHERE keyword = :query " +
            "ORDER BY lastSearchedTime DESC"
    )
    suspend fun getSearchKeywords(query: String): List<SearchKeywordCacheDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    @Delete
    suspend fun deleteSearchKeyword(searchKeyword: SearchKeywordCacheDto)

    @Query("DELETE FROM SEARCH_KEYWORD_TABLE")
    suspend fun clearSearchHistory()

}