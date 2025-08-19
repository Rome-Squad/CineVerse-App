package com.giraffe.user.usecase

import com.giraffe.user.dummydata.fakeUser
import com.giraffe.user.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class RefreshUserUseCaseTest {
    val userRepository: UserRepository = mockk()
    val refreshUserUseCase = RefreshUserUseCase(userRepository)

    @Test
    fun `invoke should call refreshUser on repository`() = runTest {

        coEvery { userRepository.refreshUser() } returns fakeUser

        refreshUserUseCase()

        coVerify(exactly = 1) { userRepository.refreshUser() }
    }

}