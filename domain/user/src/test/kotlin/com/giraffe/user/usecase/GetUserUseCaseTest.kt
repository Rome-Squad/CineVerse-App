package com.giraffe.user.usecase

import com.giraffe.user.dummydata.fakeUser
import com.giraffe.user.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetUserUseCaseTest {

    private val userRepository: UserRepository = mockk()
    private val getUserUseCase: GetUserUseCase = GetUserUseCase(userRepository)

    @Test
    fun `invoke should call getAccountDetails on repository`() = runTest {

        coEvery { userRepository.getUser() } returns fakeUser

        getUserUseCase()

        coVerify(exactly = 1) { userRepository.getUser() }
    }

    @Test
    fun `invoke should return account details from repository`() = runTest {

        coEvery { userRepository.getUser() } returns fakeUser

        val result = getUserUseCase()

        assertThat(result).isEqualTo(fakeUser)
    }

}