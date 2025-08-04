package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.fake.createFakeSeries
import com.giraffe.media.series.entity.Series
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCollectionSeriesUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var getCollectionSeriesUseCase: GetCollectionSeriesUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk()
        getCollectionSeriesUseCase = GetCollectionSeriesUseCase(collectionsRepository)
    }

    @Test
    fun `should return list of series when collectionId is valid`() = runTest {
        // Given
        val collectionId = 9
        val expectedSeries = listOf(
            createFakeSeries(id = 1, name = "Breaking Bad"),
            createFakeSeries(id = 2, name = "The Crown")
        )
        coEvery { collectionsRepository.getCollectionSeries(collectionId) } returns expectedSeries

        // When
        val result: List<Series> = getCollectionSeriesUseCase(collectionId)

        // Then
        assertThat(result).isEqualTo(expectedSeries)
    }
}
