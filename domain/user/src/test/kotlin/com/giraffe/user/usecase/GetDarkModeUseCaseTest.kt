package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetDarkModeUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk()
    private val getDarkModeUseCase: GetDarkModeUseCase = GetDarkModeUseCase(settingsRepository)

    @Test
    fun `invoke should call isDarkMode on repository`() = runTest {

        every { settingsRepository.isDarkMode() } returns flowOf(true)

        getDarkModeUseCase()

        verify(exactly = 1) { settingsRepository.isDarkMode() }
    }

    @Test
    fun `invoke should return dark mode state from repository`() = runTest {

        val isDark = true
        every { settingsRepository.isDarkMode() } returns flowOf(isDark)

        val resultFlow = getDarkModeUseCase()

        assertThat(resultFlow.first()).isEqualTo(isDark)
    }

}