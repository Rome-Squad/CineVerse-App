package com.giraffe.media.search.usecase

import com.giraffe.media.search.dummydata.searchDummyData
import com.giraffe.media.search.repository.SearchRepository
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
