package com.giraffe.person.usecase


import com.giraffe.person.entity.PersonCredit
import com.giraffe.person.entity.PersonDetails
import com.giraffe.person.entity.PersonInfo
import com.giraffe.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetPersonDetailsUseCaseTest {

    private lateinit var repository: PersonRepository
    private lateinit var getPersonDetailsUseCase: GetPersonDetailsUseCase

    private val expectedPersonDetails = PersonDetails(
        personInfo = PersonInfo(
            id = 3895,
            name = "Michael Caine",
            biography = "A legendary British actor...",
            birthday = "1933-03-14",
            placeOfBirth = "Rotherhithe, London, England, UK",
            imageUrl = "https://image.tmdb.org/t/p/w500/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg"
        ),
        images = listOf(
            "/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg",
            "/r3U4n9Ti6UrY5m84wzJVTKeBgeC.jpg"
        ),
        movieCredits = listOf(
            PersonCredit(
                id = 155,
                title = "The Dark Knight",
                posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                voteAverage = 8.5
            )
        ),
        tvCredits = listOf(
            PersonCredit(
                id = 217,
                title = "Inside the Actors Studio",
                posterPath = "/a6O7gKJQe5HWaMujYvdMYaj9PnO.jpg",
                voteAverage = 7.5
            )
        )
    )

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getPersonDetailsUseCase = GetPersonDetailsUseCase(repository)
    }

    @Test
    fun `should return person details from repository`() = runTest {
        // Given
        val personId = 3895
        coEvery { repository.getPersonDetails(personId) } returns expectedPersonDetails

        // When
        val result = getPersonDetailsUseCase(personId)

        // Then
        assertThat(result).isEqualTo(expectedPersonDetails)
    }
}