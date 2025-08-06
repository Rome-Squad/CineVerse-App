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
import org.junit.jupiter.api.Test

class GetLanguageUseCaseTest {

    private lateinit var settingsRepository: SettingsRepository
    private lateinit var getLanguageUseCase: GetLanguageUseCase

    @BeforeEach
    fun setUp() {
        settingsRepository = mockk()
        getLanguageUseCase = GetLanguageUseCase(settingsRepository)
    }

    @Test
    fun `invoke should call getLanguage on repository`() = runTest {
        // given
        every { settingsRepository.getLanguage() } returns flowOf("ar")

        // when
        getLanguageUseCase()

        // then
        verify(exactly = 1) { settingsRepository.getLanguage() }
    }

    @Test
    fun `invoke should return language from repository`() = runTest {
        // given
        val expectedLang = "ar"
        every { settingsRepository.getLanguage() } returns flowOf(expectedLang)

        // when
        val resultFlow = getLanguageUseCase()

        // then
        assertThat(resultFlow.first()).isEqualTo(expectedLang)
    }
}