package com.giraffe.series.database

import androidx.room.*
import com.giraffe.series.dto.SearchCacheEntity

@Dao
interface SearchCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchCache(cache: SearchCacheEntity)

    @Query("SELECT * FROM search_cache WHERE keyword = :keyword")
    suspend fun getCacheForKeyword(keyword: String): SearchCacheEntity?

    @Query("DELETE FROM search_cache WHERE keyword = :keyword")
    suspend fun deleteCacheForKeyword(keyword: String)

    @Query("SELECT * FROM search_cache")
    suspend fun getAllCaches(): List<SearchCacheEntity>

    @Query("DELETE FROM search_cache")
    suspend fun clearAll()
}
