package com.giraffe.media.explore.cleaner

import com.giraffe.media.explore.dao.ExploreSearchKeywordDao
import javax.inject.Inject

class KeywordsCacheCleanerImp @Inject constructor(
    private val dao: ExploreSearchKeywordDao
) : KeywordsCacheCleaner {
    override suspend fun clearExpiredKeywordsCache() = dao.clearExpiredKeywordPagesCache(
        System.currentTimeMillis()
    )
}