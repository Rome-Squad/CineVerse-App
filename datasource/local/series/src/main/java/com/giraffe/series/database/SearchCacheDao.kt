package com.giraffe.series.database

import androidx.room.*
import com.giraffe.series.model.SearchCacheDto
import com.giraffe.series.utils.DatabaseConstants.SEARCH_KEYWORD_TABLE_CACHE

@Dao
interface SearchCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchCache(cache: SearchCacheDto)

    @Query("SELECT * FROM $SEARCH_KEYWORD_TABLE_CACHE WHERE LOWER(keyword) = LOWER(:keyword)")
    suspend fun getCacheForKeyword(keyword: String): SearchCacheDto?

    @Query("DELETE FROM $SEARCH_KEYWORD_TABLE_CACHE WHERE LOWER(keyword) = LOWER(:keyword)")
    suspend fun deleteCacheForKeyword(keyword: String)

    @Query("SELECT * FROM $SEARCH_KEYWORD_TABLE_CACHE")
    suspend fun getAllCaches(): List<SearchCacheDto>

    @Query("DELETE FROM $SEARCH_KEYWORD_TABLE_CACHE")
    suspend fun clearAll()
}