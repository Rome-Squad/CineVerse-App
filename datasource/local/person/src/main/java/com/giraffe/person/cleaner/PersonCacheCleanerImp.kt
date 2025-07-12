package com.giraffe.person.cleaner

import com.giraffe.person.dao.PersonDao

class PersonCacheCleanerImp(private val dao: PersonDao):PersonCacheCleaner {
    override suspend fun clearPersonCache() {
        dao.clearPersonCache(System.currentTimeMillis())
    }
}