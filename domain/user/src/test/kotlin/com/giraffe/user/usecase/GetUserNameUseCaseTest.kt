package com.giraffe.user.usecase

import com.giraffe.user.dummydata.fakeUser
import com.giraffe.user.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetUserNameUseCaseTest {
    val getUserUseCase: GetUserUseCase = mockk()
    val userRepository: UserRepository = mockk()
    val getUserNameUseCase = GetUserNameUseCase(getUserUseCase, userRepository)

    @Test
    fun `when user exists and has non-empty displayName should return displayName`() = runTest {

        every { getUserUseCase() } returns flowOf(fakeUser)

        val result = getUserNameUseCase().first()

        assertThat(result).isEqualTo("hamada")
    }

    @Test
    fun `when user exists and has empty displayName should return username`() = runTest {
        val fakeUser1 = fakeUser.copy(displayName = "")

        every { getUserUseCase() } returns flowOf(fakeUser1)

        val result = getUserNameUseCase().first()

        assertThat(result).isEqualTo("hamada_rayyan")
    }

    @Test
    fun `when user is null should refresh and return username`() = runTest {
        val refreshedUser = fakeUser.copy(username = "refreshed_user")

        every { getUserUseCase() } returns flowOf(null)
        coEvery { userRepository.refreshUser() } returns refreshedUser

        val result = getUserNameUseCase().first()

        assertThat(result).isEqualTo("refreshed_user")
    }
}