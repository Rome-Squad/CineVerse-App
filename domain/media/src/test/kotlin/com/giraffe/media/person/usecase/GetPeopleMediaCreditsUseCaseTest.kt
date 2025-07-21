package com.giraffe.media.person.usecase

import com.giraffe.media.exception.MediaException
import com.giraffe.media.person.entity.PersonCredit
import com.giraffe.media.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetPeopleMediaCreditsUseCaseTest {

    private lateinit var repository: PersonRepository
    private lateinit var getPeopleMediaCreditsUseCase: GetPeopleMediaCreditsUseCase
    val personID = 1

    private fun createPersonCredit(
        id: Int,
        title: String = "Title",
        posterPath: String? = null,
        voteAverage: Double = 8.5,
        mediaType: String = "movie"
    ) = PersonCredit(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        mediaType = mediaType
    )

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getPeopleMediaCreditsUseCase = GetPeopleMediaCreditsUseCase(repository)
    }

    @Test
    fun `should return list of media credits when repository call success`() = runTest {
        val expectedMediaCredits = listOf(
            createPersonCredit(
                id = 1,
                title = "Clean Slate",
            ),
            createPersonCredit(
                id = 2,
                title = "An Audience with Adele",
            ),
            createPersonCredit(
                id = 3,
                title = "Breaking Bad",
            )
        )
        coEvery { repository.getPeopleMediaCredits(personID) } returns expectedMediaCredits

        val result = getPeopleMediaCreditsUseCase(personID)

        assertThat(result).isEqualTo(expectedMediaCredits)
    }

    @Test
    fun `should return empty list when person has no media credits`() = runTest {
        coEvery { repository.getPeopleMediaCredits(personID) } returns emptyList()

        val result = getPeopleMediaCreditsUseCase(personID)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should propagate MediaException when repository throws`() = runTest {
        coEvery { repository.getPeopleMediaCredits(personID) } throws MediaException()

        assertThrows<MediaException> {
            getPeopleMediaCreditsUseCase(personID)
        }
    }

}