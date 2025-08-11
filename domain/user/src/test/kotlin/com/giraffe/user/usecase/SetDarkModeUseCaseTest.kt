package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SetDarkModeUseCaseTest {

    private var settingsRepository: SettingsRepository = mockk()
    private var setDarkModeUseCase: SetDarkModeUseCase = SetDarkModeUseCase(settingsRepository)

    @Test
    fun `invoke should call setDarkMode on repository with correct value`() = runTest {

    val isDark = true
        coEvery { settingsRepository.setDarkMode(isDark) } returns Unit

        setDarkModeUseCase(isDark)

        coVerify(exactly = 1) { settingsRepository.setDarkMode(isDark) }
    }
}