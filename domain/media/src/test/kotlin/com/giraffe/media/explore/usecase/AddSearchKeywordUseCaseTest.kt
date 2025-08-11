package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.SearchRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class AddSearchKeywordUseCaseTest {

    private var repository: SearchRepository = mockk(relaxed = true)
    private var useCase: AddSearchKeywordUseCase = AddSearchKeywordUseCase(repository)

    @Test
    fun `should call repository with given search keyword when execute is invoked`() = runTest {

        useCase("sci-fi")

        coVerify { repository.addSearchKeyword("sci-fi") }
    }
}
