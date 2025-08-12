package com.giraffe.user.usecase

import com.giraffe.user.repository.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SetLanguageUseCaseTest {
    private var settingsRepository: SettingsRepository = mockk()
    private var setLanguageUseCase: SetLanguageUseCase = SetLanguageUseCase(settingsRepository)

    @Test
    fun `invoke should call setLanguage on repository with correct code`() = runTest {

        val langCode = "ar"
        coEvery { settingsRepository.setLanguage(langCode) } returns Unit

        setLanguageUseCase(langCode)

        coVerify(exactly = 1) { settingsRepository.setLanguage(langCode) }
    }
}