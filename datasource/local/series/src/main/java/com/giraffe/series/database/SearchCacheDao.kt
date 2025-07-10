package com.giraffe.series.database

import androidx.room.*
import com.giraffe.series.model.CachedSearchCacheDto
@Dao
interface SearchCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchCache(cache: CachedSearchCacheDto)

    @Query("SELECT * FROM SEARCH_KEYWORD_TABLE_CACHE WHERE LOWER(keyword) = LOWER(:keyword)")
    suspend fun getCacheForKeyword(keyword: String): CachedSearchCacheDto?

    @Query("DELETE FROM SEARCH_KEYWORD_TABLE_CACHE WHERE LOWER(keyword) = LOWER(:keyword)")
    suspend fun deleteCacheForKeyword(keyword: String)

    @Query("SELECT * FROM SEARCH_KEYWORD_TABLE_CACHE")
    suspend fun getAllCaches(): List<CachedSearchCacheDto>

    @Query("DELETE FROM SEARCH_KEYWORD_TABLE_CACHE")
    suspend fun clearAll()
}
