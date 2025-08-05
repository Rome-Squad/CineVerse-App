package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SetDarkModeUseCaseTest {

    private lateinit var settingsRepository: SettingsRepository
    private lateinit var setDarkModeUseCase: SetDarkModeUseCase

    @BeforeEach
    fun setUp() {
        settingsRepository = mockk()
        setDarkModeUseCase = SetDarkModeUseCase(settingsRepository)
    }

    @Test
    fun `invoke should call setDarkMode on repository with correct value`() = runTest {
        // given
        val isDark = true
        coEvery { settingsRepository.setDarkMode(isDark) } returns Unit

        // when
        setDarkModeUseCase(isDark)

        // then
        coVerify(exactly = 1) { settingsRepository.setDarkMode(isDark) }
    }
}