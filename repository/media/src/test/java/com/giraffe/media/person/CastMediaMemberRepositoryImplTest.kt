package com.giraffe.media.person

import com.giraffe.media.exception.MediaException
import com.giraffe.media.person.datasource.local.MediaMemberLocalDataSource
import com.giraffe.media.person.datasource.local.cacheDto.PersonCacheDto
import com.giraffe.media.person.datasource.remote.MediaMemberRemoteDataSource
import com.giraffe.media.person.mapper.toCastMemberEntity
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class CastMediaMemberRepositoryImplTest {
    private val remoteDataSource: MediaMemberRemoteDataSource = mockk(relaxed = true)
    private val localDataSource: MediaMemberLocalDataSource = mockk(relaxed = true)
    private val repository = MediaMemberRepositoryImpl(remoteDataSource, localDataSource)
    private val keyword = "Mohannad"
    private val dummyPersonCacheDto = PersonCacheDto(
        id = 5,
        name = "Mohannad",
        role = "Acting",
        type = "Cast",
    )
    private val dummyPeopleDto =
        listOf(dummyPersonCacheDto, dummyPersonCacheDto, dummyPersonCacheDto)

    @Test
    fun `should throw MediaDomainException when remote search throw any exception`() = runTest {
        //given
        coEvery { localDataSource.searchByName(keyword, 1) } returns emptyList()
        coEvery { remoteDataSource.searchForActorByName(keyword, 1) } throws Exception()
        //when && then
        assertThrows<MediaException> { repository.getActorByName(keyword, 1) }
        coVerify(exactly = 0) { localDataSource.insertPerson(any()) }
    }


    @Test
    fun `should cache person as recent when call storeRecentPerson`() = runTest {
        //given
        coEvery { localDataSource.insertPerson(dummyPersonCacheDto) } just Runs
        //when
        repository.addCastToRecentViewed(dummyPersonCacheDto.toCastMemberEntity())
        //then
        coVerify(exactly = 1) { localDataSource.insertPerson(match { it.isRecent }) }
    }

    @Test
    fun `should throw MediaDomainException when local data source throw any exception`() = runTest {
        coEvery { localDataSource.insertPerson(any()) } throws Exception()

        assertThrows<MediaException> { repository.addCastToRecentViewed(dummyPersonCacheDto.toCastMemberEntity()) }
    }


    @Test
    fun `should get recent people when local data source has recent people`() = runTest {
        coEvery { localDataSource.getRecentPeople() } returns dummyPeopleDto

        val result = repository.getRecentMediaMembers()

        assertThat(result).isEqualTo(dummyPersonCacheDto)
    }

    @Test
    fun `should throw MediaDomainException when getRecentPeople throw any exception`() = runTest {
        coEvery { localDataSource.getRecentPeople() } throws Exception()

        assertThrows<MediaException> { repository.getRecentMediaMembers() }
    }

    @Test
    fun `should clear recent people when local data source has recent people`() = runTest {
        coEvery { localDataSource.clearRecentPeople() } just Runs

        repository.clearRecentViewed()

        coVerify(exactly = 1) { repository.clearRecentViewed() }
    }

    @Test
    fun `should throw MediaDomainException when clearRecentPeople throw any exception`() = runTest {
        coEvery { localDataSource.clearRecentPeople() } throws Exception()

        assertThrows<MediaException> { repository.clearRecentViewed() }
    }
}