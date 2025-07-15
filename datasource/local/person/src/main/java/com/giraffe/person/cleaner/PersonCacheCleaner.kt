package com.giraffe.media.person.cleaner

interface PersonCacheCleaner {
    suspend fun clearPersonCache()
}