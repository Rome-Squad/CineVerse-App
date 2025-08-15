package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ClearCollectionsCacheUseCaseTest {
    private val collectionsRepository: CollectionsRepository = mockk(relaxed = true)
    private val clearCollectionsCacheUseCase = ClearCollectionsCacheUseCase(collectionsRepository)

    @Test
    fun `should call clear collection cache method in repository when invoke the usecase `() = runTest {

        clearCollectionsCacheUseCase()

        coVerify { collectionsRepository.clearCollectionsCache() }
    }

}