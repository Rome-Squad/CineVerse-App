package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.util.createFakeCollection
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetCollectionDetailsUseCaseTest {

    private val collectionsRepository: CollectionsRepository = mockk()
    private val getCollectionDetailsUseCase = GetCollectionDetailsUseCase(collectionsRepository)

    @Test
    fun `should return collection details when collectionId is valid`() = runTest {
        val collectionId = 5
        val expectedCollection = createFakeCollection(id = collectionId)
        coEvery { collectionsRepository.getCollectionDetails(collectionId) } returns expectedCollection

        val result: Collection = getCollectionDetailsUseCase(collectionId)

        assertThat(result).isEqualTo(expectedCollection)
    }
}
