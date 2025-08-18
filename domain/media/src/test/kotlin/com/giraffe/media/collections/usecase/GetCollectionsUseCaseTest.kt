package com.giraffe.media.collections.usecase

import com.giraffe.media.collections.repository.CollectionsRepository
import com.giraffe.media.collections.util.createFakeCollection
import com.giraffe.user.entity.User
import com.giraffe.user.usecase.GetUserUseCase
import com.giraffe.user.usecase.IsLoggedInByAccountUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetCollectionsUseCaseTest {

    private val collectionsRepository: CollectionsRepository = mockk()
    private val getUserUseCase: GetUserUseCase = mockk()
    private val isLoggedInUseCase: IsLoggedInByAccountUseCase = mockk()
    private val getCollectionsUseCase =
        GetCollectionsUseCase(collectionsRepository, getUserUseCase, isLoggedInUseCase)
    private val getCollectionsUseCase = GetCollectionsUseCase(collectionsRepository, getUserUseCase)

    @Test
    fun `should return list of collections when GetCollectionsUseCase invoked`() = runTest {

        val fakeUser = User(id = 111, displayName = "test", username = "test", avatarUrl = null)
        val expectedCollections = listOf(
            createFakeCollection(id = 1, name = "Favorites"),
            createFakeCollection(id = 2, name = "Watch Later")
        )

        every { getUserUseCase() } returns flowOf(fakeUser)
        every { collectionsRepository.getCollections(fakeUser.id) } returns flowOf(
            expectedCollections
        )

        val result = getCollectionsUseCase().first()

        assertThat(result).isEqualTo(expectedCollections)
    }
}
