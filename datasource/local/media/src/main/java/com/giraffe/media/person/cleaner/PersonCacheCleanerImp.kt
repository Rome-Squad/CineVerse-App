package com.giraffe.media.person.cleaner

import com.giraffe.media.person.dao.PersonDao

class PersonCacheCleanerImp(private val dao: PersonDao):PersonCacheCleaner {
    override suspend fun clearPersonCache() {
        dao.clearPersonCache(System.currentTimeMillis())
    }
}