package com.giraffe.media.mediaMember.usecase


import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.util.createCastMember
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetCastDetailsUseCaseTest {

    private lateinit var repository: MediaMemberRepository
    private lateinit var getCastDetailsUseCase: GetCastDetailsUseCase

    private val expectedPersonDetails = createCastMember(
        id = 3895,
        name = "Michael Caine",
        biography = "A legendary British actor...",
        birthday = "1933-03-14",
        placeOfBirth = "Rotherhithe, London, England, UK",
        imageUrl = "https://image.tmdb.org/t/p/w500/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg",
        otherImages = listOf(
            "/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg",
            "/r3U4n9Ti6UrY5m84wzJVTKeBgeC.jpg"
        ),
        socialMedia = SocialMediaLinks(
            facebookLink = "michaelcaine",
            instagramLink = "michaelcaine_official",
            youtubeLink = "UCV1n8z2d3a5b6c7d8e9f0g1h"
        ),
        role = "Actor"
    )

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getCastDetailsUseCase = GetCastDetailsUseCase(repository)
    }

    @Test
    fun `should return person details from repository`() = runTest {
        // Given
        val personId = 3895
        coEvery { repository.getCastDetailsById(personId) } returns expectedPersonDetails

        // When
        val result = getCastDetailsUseCase(personId)

        // Then
        assertThat(result).isEqualTo(expectedPersonDetails)
    }
}