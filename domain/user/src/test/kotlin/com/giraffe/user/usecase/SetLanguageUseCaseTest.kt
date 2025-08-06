package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SetLanguageUseCaseTest {

    private lateinit var settingsRepository: SettingsRepository
    private lateinit var setLanguageUseCase: SetLanguageUseCase

    @BeforeEach
    fun setUp() {
        settingsRepository = mockk()
        setLanguageUseCase = SetLanguageUseCase(settingsRepository)
    }

    @Test
    fun `invoke should call setLanguage on repository with correct code`() = runTest {
        // given
        val langCode = "ar"
        coEvery { settingsRepository.setLanguage(langCode) } returns Unit

        // when
        setLanguageUseCase(langCode)

        // then
        coVerify(exactly = 1) { settingsRepository.setLanguage(langCode) }
    }
}