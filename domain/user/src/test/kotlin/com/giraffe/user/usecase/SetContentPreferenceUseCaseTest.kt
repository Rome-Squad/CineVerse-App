package com.giraffe.user.usecase

import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.repository.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SetContentPreferenceUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var setContentPreferenceUseCase: SetContentPreferenceUseCase

    @BeforeEach
    fun setUp() {
        settingsRepository = mockk()
        setContentPreferenceUseCase = SetContentPreferenceUseCase(settingsRepository)
    }

    @Test
    fun `invoke should call setContentPreference on repository with correct preference`() =
        runTest {
            // Given
            val preferenceToSet = ContentPreference.HIDE_EXPLICIT
            coEvery { settingsRepository.setContentPreference(preferenceToSet) } returns Unit

            // When
            setContentPreferenceUseCase(preferenceToSet)

            // Then
            coVerify(exactly = 1) { settingsRepository.setContentPreference(preferenceToSet) }
        }
}