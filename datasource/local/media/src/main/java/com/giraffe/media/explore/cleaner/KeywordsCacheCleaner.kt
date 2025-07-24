package com.giraffe.media.explore.cleaner

interface KeywordsCacheCleaner {
    suspend fun clearExpiredKeywordsCache()
}