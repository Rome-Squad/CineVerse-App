package com.giraffe.user.usecase

import com.giraffe.user.dummydata.fakeUser
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class GetUserNameUseCaseTest {
    private val getUserUseCase: GetUserUseCase = mockk()
    private val getUserNameUseCase: GetUserNameUseCase = GetUserNameUseCase(getUserUseCase)

    @Test
    fun `invoke should call getUser on repository`() = runTest {

        coEvery { getUserUseCase.invoke() } returns fakeUser

        getUserNameUseCase()

        coVerify(exactly = 1) { getUserUseCase.invoke() }
    }

    @Test
    fun `invoke when user has non-empty displayName should return displayName`() = runTest {

        coEvery { getUserUseCase.invoke() } returns fakeUser

        val result = getUserNameUseCase()

        assertThat(result).isEqualTo(fakeUser.displayName)
    }

    @Test
    fun `invoke when user has empty displayName should return username`() = runTest {

        val userWithoutDisplayName = fakeUser.copy(displayName = "")
        coEvery { getUserUseCase.invoke() } returns userWithoutDisplayName

        val result = getUserNameUseCase()

        assertThat(result).isEqualTo(fakeUser.username)
    }

    @Test
    fun `invoke when repository throws exception should rethrow the exception`() = runTest {

        val expectedException = IOException("Network failed")
        coEvery { getUserUseCase.invoke() } throws expectedException

        val actualException = assertFailsWith<IOException> {
            getUserNameUseCase()
        }

        assertThat(actualException).isEqualTo(expectedException)
    }
}