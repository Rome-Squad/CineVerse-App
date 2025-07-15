package com.giraffe.media.person.usecase

import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetPeopleByMovieIdUseCaseTest {

    private lateinit var repository: PersonRepository
    private lateinit var getPeopleByMovieIdUseCase: GetPeopleByMovieIdUseCase

    private val expectedPeople = listOf(
        Person(
            id = 1,
            name = "Christian Bale",
            role = "Bruce Wayne / Batman",
            imageUrl = "https://image.tmdb.org/t/p/w500/christian_bale.jpg"
        ),
        Person(
            id = 2,
            name = "Heath Ledger",
            role = "The Joker",
            imageUrl = "https://image.tmdb.org/t/p/w500/heath_ledger.jpg"
        )
    )

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getPeopleByMovieIdUseCase = GetPeopleByMovieIdUseCase(repository)
    }

    @Test
    fun `should return list of people when repository returns people for movieId`() = runTest {
        // Given
        val movieId = 123
        coEvery { repository.getPeopleByMovieId(movieId) } returns expectedPeople

        // When
        val result = getPeopleByMovieIdUseCase(movieId)

        // Then
        assertThat(result).isEqualTo(expectedPeople)
    }
}