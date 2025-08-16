package com.giraffe.media.search.usecase

import com.giraffe.media.search.repository.SearchRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ClearExpiredSearchHistoryUseCaseTest {

    private var repository: SearchRepository = mockk(relaxed = true)
    private var useCase: ClearExpiredSearchHistoryUseCase =
        ClearExpiredSearchHistoryUseCase(repository)

    @Test
    fun `invoked should call clearExpiredSearch form repository to clear expired search history`() =
        runTest {

            useCase.invoke()

            coVerify(exactly = 1) { repository.clearExpiredSearch() }
        }
}
