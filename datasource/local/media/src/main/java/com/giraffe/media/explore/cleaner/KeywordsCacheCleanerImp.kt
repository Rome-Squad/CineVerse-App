package com.giraffe.media.explore.cleaner

import com.giraffe.media.explore.dao.SearchKeywordDao
import javax.inject.Inject

class KeywordsCacheCleanerImp @Inject constructor(
    private val dao: SearchKeywordDao
) : KeywordsCacheCleaner {
    override suspend fun clearExpiredKeywordsCache() = dao.clearExpiredKeywordPagesCache(
        System.currentTimeMillis()
    )
}