package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.util.createFakeCollection
import com.giraffe.media.exception.ValidationException
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AddCollectionUseCaseTest {

    private lateinit var collectionsRepository: CollectionsRepository
    private lateinit var addCollectionUseCase: AddCollectionUseCase

    @BeforeEach
    fun setUp() {
        collectionsRepository = mockk(relaxed = true)
        addCollectionUseCase = AddCollectionUseCase(collectionsRepository)
    }

    @Test
    fun `should add collection successfully when collection name is valid`() = runTest {
        // Given
        val collection = createFakeCollection(name = "Action")

        // When
        addCollectionUseCase(collection)

        // Then
        coVerify { collectionsRepository.addCollection(collection) }
    }

    @Test
    fun `should throw ValidationException when collection name is blank`() = runTest {
        // Given
        val invalidCollection = createFakeCollection(name = "")

        // When / Then
        val exception = assertThrows<ValidationException> {
            addCollectionUseCase(invalidCollection)
        }

        assertThat(exception).hasMessageThat().isEqualTo("Collection name cannot be blank")
    }
}
