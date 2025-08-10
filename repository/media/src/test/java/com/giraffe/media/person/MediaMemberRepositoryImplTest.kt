package com.giraffe.media.person

import com.giraffe.media.exception.MediaException
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.person.datasource.local.PersonLocalDataSource
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
import com.giraffe.media.person.datasource.remote.dto.MediaMemberDto
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.mapper.toEntity
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

class MediaMemberRepositoryImplTest {
    private lateinit var repository: MediaMemberRepository
    private val remoteDataSource: MediaMemberRemoteDataSource = mockk(relaxed = true)
    private val localDataSource: PersonLocalDataSource = mockk(relaxed = true)
    private val keyword = "Mohannad"
    private val dummyPersonCacheDto = PersonCacheDto(
        id = 5,
        name = "Mohannad",
        role = "Acting",
        type = "Cast",
    )
    private val dummyPersonResponse = MediaMemberDto(
        adult = false,
        gender = 1,
        id = 5,
        knownFor = listOf(),
        department = "Actor",
        name = "Mohannad",
        originalName = "مهند",
        popularity = 100.0,
    )

    private val dummyPeopleDto =
        listOf(dummyPersonCacheDto, dummyPersonCacheDto, dummyPersonCacheDto)
    private val dummyPeopleResponse = listOf(dummyPersonResponse, dummyPersonResponse)

    @Before
    fun setup() {
        repository = MediaMemberRepositoryImpl(
            remoteDataSource,
            localDataSource
        )
    }

    @Test
    fun `should throw MediaDomainException when remote search throw any exception`() = runTest {
        //given
        coEvery { localDataSource.searchByName(keyword, 1) } returns emptyList()
        coEvery { remoteDataSource.searchForActorByName(keyword, 1) } throws Exception()
        //when && then
        assertThrows<MediaException> { repository.searchForActorByName(keyword, 1) }
        coVerify(exactly = 0) { localDataSource.insertPerson(any()) }
    }


    @Test
    fun `should cache person as recent when call storeRecentPerson`() = runTest {
        //given
        coEvery { localDataSource.insertPerson(dummyPersonCacheDto) } just Runs
        //when
        repository.addCastToRecentViewed(dummyPersonCacheDto.toEntity())
        //then
        coVerify(exactly = 1) { localDataSource.insertPerson(match { it.isRecent }) }
    }

    @Test
    fun `should throw MediaDomainException when local data source throw any exception`() = runTest {
        //given
        coEvery { localDataSource.insertPerson(any()) } throws Exception()
        //when && then
        assertThrows<MediaException> { repository.addCastToRecentViewed(dummyPersonCacheDto.toEntity()) }
    }


    @Test
    fun `should get recent people when local data source has recent people`() = runTest {
        //given
        coEvery { localDataSource.getRecentPeople() } returns dummyPeopleDto
        //when
        val result = repository.getRecentMediaMembers()
        //then
        assertThat(result.size).isEqualTo(3)
        assertThat(result.first()).isInstanceOf(Person::class.java)
    }

    @Test
    fun `should throw MediaDomainException when getRecentPeople throw any exception`() = runTest {
        //given
        coEvery { localDataSource.getRecentPeople() } throws Exception()
        //when && then
        assertThrows<MediaException> { repository.getRecentMediaMembers() }
    }

    @Test
    fun `should clear recent people when local data source has recent people`() = runTest {
        //given
        coEvery { localDataSource.clearRecentPeople() } just Runs
        //when
        repository.clearRecentViewed()
        //then
        coVerify(exactly = 1) { repository.clearRecentViewed() }
    }

    @Test
    fun `should throw MediaDomainException when clearRecentPeople throw any exception`() = runTest {
        //given
        coEvery { localDataSource.clearRecentPeople() } throws Exception()
        //when && then
        assertThrows<MediaException> { repository.clearRecentViewed() }
    }
}