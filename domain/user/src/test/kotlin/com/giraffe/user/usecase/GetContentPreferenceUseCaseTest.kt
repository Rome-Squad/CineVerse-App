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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetContentPreferenceUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var getContentPreferenceUseCase: GetContentPreferenceUseCase

    @BeforeEach
    fun setUp() {
        settingsRepository = mockk()
        getContentPreferenceUseCase = GetContentPreferenceUseCase(settingsRepository)
    }

    @Test
    fun `invoke should call getContentPreference on repository`() = runTest {
        // Given
        every { settingsRepository.getContentPreference() } returns flowOf(ContentPreference.STRICT)

        // When
        getContentPreferenceUseCase()

        // Then
        verify(exactly = 1) { settingsRepository.getContentPreference() }
    }


    @Test
    fun `invoke should return content preference from repository`() = runTest {
        // Given
        val expectedPreference = ContentPreference.SHOW_ALL
        every { settingsRepository.getContentPreference() } returns flowOf(expectedPreference)

        // When
        val resultFlow = getContentPreferenceUseCase()

        // Then
        assertThat(resultFlow.first()).isEqualTo(expectedPreference)
    }
}