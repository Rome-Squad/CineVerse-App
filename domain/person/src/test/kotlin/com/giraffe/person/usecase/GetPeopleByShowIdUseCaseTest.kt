package com.giraffe.person.usecase

import com.giraffe.person.entity.Person
import com.giraffe.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetPeopleByShowIdUseCaseTest {

    private lateinit var repository: PersonRepository
    private lateinit var getPeopleByShowIdUseCase: GetPeopleByShowIdUseCase

    private val expectedPeople = listOf(
        Person(
            id = 10,
            name = "Lee Jung-jae",
            role = "Seong Gi-hun / Player 456",
            imageUrl = "https://image.tmdb.org/t/p/w500/3h5Cfm0X8ohWn7psZkqdNWqXAHH.jpg",
        ),
        Person(
            id = 11,
            name = "Wi Ha-jun",
            role = "Detective Hwang Jun-ho",
            imageUrl = "https://image.tmdb.org/t/p/w500/tEZuIaMESdBw4LfNq3vshGR4VlP.jpg",
        )
    )

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getPeopleByShowIdUseCase = GetPeopleByShowIdUseCase(repository)
    }

    @Test
    fun `should return list of people when repository returns people for showId`() = runTest {
        // Given
        val showId = 93405
        coEvery { repository.getPeopleByShowId(showId) } returns expectedPeople

        // When
        val result = getPeopleByShowIdUseCase(showId)

        // Then
        assertThat(result).isEqualTo(expectedPeople)
    }
}