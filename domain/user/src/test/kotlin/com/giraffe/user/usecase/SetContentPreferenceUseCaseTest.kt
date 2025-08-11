package com.giraffe.user.usecase

import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.repository.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SetContentPreferenceUseCaseTest {
    private var settingsRepository: SettingsRepository = mockk()
    private var setContentPreferenceUseCase: SetContentPreferenceUseCase =
        SetContentPreferenceUseCase(settingsRepository)

    @Test
    fun `invoke should call setContentPreference on repository with correct preference`() =
        runTest {

        val preferenceToSet = ContentPreference.HIDE_EXPLICIT
            coEvery { settingsRepository.setContentPreference(preferenceToSet) } returns Unit

            setContentPreferenceUseCase(preferenceToSet)

            coVerify(exactly = 1) { settingsRepository.setContentPreference(preferenceToSet) }
        }
}