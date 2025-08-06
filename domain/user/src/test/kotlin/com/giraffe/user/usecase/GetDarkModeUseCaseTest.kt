package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetDarkModeUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var getDarkModeUseCase: GetDarkModeUseCase

    @BeforeEach
    fun setUp() {
        settingsRepository = mockk()
        getDarkModeUseCase = GetDarkModeUseCase(settingsRepository)
    }

    @Test
    fun `invoke should call isDarkMode on repository`() = runTest {
        // given
        every { settingsRepository.isDarkMode() } returns flowOf(true)

        // when
        getDarkModeUseCase()

        // then
        verify(exactly = 1) { settingsRepository.isDarkMode() }
    }

    @Test
    fun `invoke should return dark mode state from repository`() = runTest {
        // given
        val isDark = true
        every { settingsRepository.isDarkMode() } returns flowOf(isDark)

        // when
        val resultFlow = getDarkModeUseCase()

        // then
        assertThat(resultFlow.first()).isEqualTo(isDark)
    }

}