package com.giraffe.media.explore.cleaner

import com.giraffe.media.explore.dao.ExploreSearchKeywordDao

class KeywordsCacheCleanerImp(
    private val dao: ExploreSearchKeywordDao
) : KeywordsCacheCleaner {
    override suspend fun clearExpiredKeywordsCache() = dao.clearExpiredKeywordPagesCache(
        System.currentTimeMillis()
    )
}