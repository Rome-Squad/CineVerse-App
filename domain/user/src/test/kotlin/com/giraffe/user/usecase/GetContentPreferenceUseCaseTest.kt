package com.giraffe.user.usecase

import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.repository.SettingsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetContentPreferenceUseCaseTest {
    private val settingsRepository: SettingsRepository = mockk()
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase =
        GetContentPreferenceUseCase(settingsRepository)

    @Test
    fun `invoke should call getContentPreference on repository`() = runTest {

        every { settingsRepository.getContentPreference() } returns flowOf(ContentPreference.STRICT)

        getContentPreferenceUseCase()

        verify(exactly = 1) { settingsRepository.getContentPreference() }
    }


    @Test
    fun `invoke should return content preference from repository`() = runTest {

        val expectedPreference = ContentPreference.SHOW_ALL
        every { settingsRepository.getContentPreference() } returns flowOf(expectedPreference)

        val resultFlow = getContentPreferenceUseCase()

        assertThat(resultFlow.first()).isEqualTo(expectedPreference)
    }
}