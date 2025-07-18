package com.giraffe.media.person

import com.giraffe.media.exception.MediaDomainException
import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.person.datasource.remote.PersonRemoteDataSource
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.mapper.toEntity
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.remote.dto.PersonDto
import com.giraffe.media.person.repository.PersonRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class PersonRepositoryImplTest {
    private lateinit var repository: PersonRepository
    private val remoteDataSource: PersonRemoteDataSource = mockk(relaxed = true)
    private val localDataSource: PersonLocalDataSource = mockk(relaxed = true)
    private val keyword = "Mohannad"
    private val dummyPersonCacheDto = PersonCacheDto(
        id = 5,
        name = "Mohannad",
        role = "Acting",
        type = "Cast",
    )
    private val dummyPersonResponse = PersonDto(
        adult = false,
        gender = 1,
        id = 5,
        knownFor = listOf(),
        role = "Actor",
        name = "Mohannad",
        originalName = "مهند",
        popularity = 100.0,
    )

    private val dummyPeopleDto =
        listOf(dummyPersonCacheDto, dummyPersonCacheDto, dummyPersonCacheDto)
    private val dummyPeopleResponse = listOf(dummyPersonResponse, dummyPersonResponse)

    @Before
    fun setup() {
        repository = PersonRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `should get cached result first when search by name`() = runTest {
        //given
        coEvery { localDataSource.searchByName(keyword) } returns dummyPeopleDto
        //when
        val result = repository.searchByName(keyword)
        //then
        assertThat(result.size).isEqualTo(3)
        assertThat(result.first()).isInstanceOf(Person::class.java)
        coVerify(exactly = 1) { localDataSource.searchByName(match { it == keyword }) }
        coVerify(exactly = 0) { remoteDataSource.searchByName(any()) }
    }

    @Test
    fun `should get result from remote and cache it as not recent when cached result not found`() =
        runTest {
            //given
            coEvery { localDataSource.searchByName(keyword) } returns emptyList()
            coEvery { remoteDataSource.searchByName(keyword) } returns dummyPeopleResponse
            //when
            val result = repository.searchByName(keyword)
            //then
            assertThat(result.size).isEqualTo(2)
            assertThat(result.first()).isInstanceOf(Person::class.java)
            coVerify(exactly = 1) {
                localDataSource.storePeople(match { list ->
                    list.size == result.size && list.all { !it.isRecent }
                })
            }
        }

    @Test
    fun `should throw MediaDomainException when local search throw any exception`() = runTest {
        //given
        coEvery { localDataSource.searchByName(keyword) } throws Exception()
        //when && then
        assertThrows<MediaDomainException> { repository.searchByName(keyword) }
        coVerify(exactly = 0) { remoteDataSource.searchByName(any()) }
        coVerify(exactly = 0) { localDataSource.storePerson(any()) }
    }

    @Test
    fun `should throw MediaDomainException when remote search throw any exception`() = runTest {
        //given
        coEvery { localDataSource.searchByName(keyword) } returns emptyList()
        coEvery { remoteDataSource.searchByName(keyword) } throws Exception()
        //when && then
        assertThrows<MediaDomainException> { repository.searchByName(keyword) }
        coVerify(exactly = 0) { localDataSource.storePerson(any()) }
    }

    @Test
    fun `should throw MediaDomainException when local store throw any exception`() = runTest {
        //given
        coEvery { localDataSource.searchByName(keyword) } returns emptyList()
        coEvery { remoteDataSource.searchByName(keyword) } returns dummyPeopleResponse
        coEvery { localDataSource.storePeople(any()) } throws Exception("Test Exception")

        //when && then
        assertThrows<MediaDomainException> { repository.searchByName(keyword) }
    }


    @Test
    fun `should cache person as recent when call storeRecentPerson`() = runTest {
        //given
        coEvery { localDataSource.storePerson(dummyPersonCacheDto) } just Runs
        //when
        repository.storeRecentPerson(dummyPersonCacheDto.toEntity())
        //then
        coVerify(exactly = 1) { localDataSource.storePerson(match { it.isRecent }) }
    }

    @Test
    fun `should throw MediaDomainException when local data source throw any exception`() = runTest {
        //given
        coEvery { localDataSource.storePerson(any()) } throws Exception()
        //when && then
        assertThrows<MediaDomainException> { repository.storeRecentPerson(dummyPersonCacheDto.toEntity()) }
    }


    @Test
    fun `should get recent people when local data source has recent people`() = runTest {
        //given
        coEvery { localDataSource.getRecentPeople() } returns dummyPeopleDto
        //when
        val result = repository.getRecentPeople()
        //then
        assertThat(result.size).isEqualTo(3)
        assertThat(result.first()).isInstanceOf(Person::class.java)
    }

    @Test
    fun `should throw MediaDomainException when getRecentPeople throw any exception`() = runTest {
        //given
        coEvery { localDataSource.getRecentPeople() } throws Exception()
        //when && then
        assertThrows<MediaDomainException> { repository.getRecentPeople() }
    }

    @Test
    fun `should clear recent people when local data source has recent people`() = runTest {
        //given
        coEvery { localDataSource.clearRecentPeople() } just Runs
        //when
        repository.clearRecentPeople()
        //then
        coVerify(exactly = 1) { repository.clearRecentPeople() }
    }

    @Test
    fun `should throw MediaDomainException when clearRecentPeople throw any exception`() = runTest {
        //given
        coEvery { localDataSource.clearRecentPeople() } throws Exception()
        //when && then
        assertThrows<MediaDomainException> { repository.clearRecentPeople() }
    }
}