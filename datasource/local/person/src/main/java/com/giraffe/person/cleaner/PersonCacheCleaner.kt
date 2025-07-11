package com.giraffe.person.cleaner

interface PersonCacheCleaner {
    suspend fun clearPersonCache()
}