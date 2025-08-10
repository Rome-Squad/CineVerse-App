package com.giraffe.media.person.usecase


import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.GetCastDetailsUseCase
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonCredit
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetCastDetailsUseCaseTest {

    private lateinit var repository: MediaMemberRepository
    private lateinit var getCastDetailsUseCase: GetCastDetailsUseCase

    private val expectedPersonDetails = Person(
        id = 3895,
        name = "Michael Caine",
        biography = "A legendary British actor...",
        birthday = "1933-03-14",
        placeOfBirth = "Rotherhithe, London, England, UK",
        imageUrl = "https://image.tmdb.org/t/p/w500/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg",
        images = listOf(
            "/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg",
            "/r3U4n9Ti6UrY5m84wzJVTKeBgeC.jpg"
        ),
        personCredits = listOf(
            PersonCredit(
                id = 155,
                title = "The Dark Knight",
                posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                voteAverage = 8.5,
                mediaType = null
            ),
            PersonCredit(
                id = 217,
                title = "Inside the Actors Studio",
                posterPath = "/a6O7gKJQe5HWaMujYvdMYaj9PnO.jpg",
                voteAverage = 7.5,
                mediaType = null
            )
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
        coEvery { repository.getPersonDetails(personId) } returns expectedPersonDetails

        // When
        val result = getCastDetailsUseCase(personId)

        // Then
        assertThat(result).isEqualTo(expectedPersonDetails)
    }
}