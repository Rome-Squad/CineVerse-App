package com.giraffe.media.explore.usecase

import com.giraffe.media.explore.repository.SearchRepository
import com.giraffe.media.explore.util.searchDummyData
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class DeleteSearchKeywordUseCaseTest {

    private var repository: SearchRepository = mockk(relaxed = true)
    private var useCase: DeleteSearchKeywordUseCase = DeleteSearchKeywordUseCase(repository)

    @Test
    fun `should call repository with given search keyword when execute is invoked`() = runTest {

        useCase(searchDummyData.keyword)

        coVerify { repository.deleteSearchKeyword(searchDummyData.keyword) }
    }
}
