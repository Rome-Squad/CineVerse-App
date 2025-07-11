package com.giraffe.person.util

import com.giraffe.person.exception.ForbiddenAccessException
import com.giraffe.person.exception.InvalidApiKeyException
import com.giraffe.person.exception.InvalidPersonIdException
import com.giraffe.person.exception.NetworkException
import com.giraffe.person.exception.PersonNotFoundException
import com.giraffe.person.exception.RateLimitExceededException
import com.giraffe.person.exception.ServerErrorException
import com.giraffe.person.exception.UnauthorizedAccessException
import com.giraffe.person.exception.UnknownException
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SafeCallTest {
    private lateinit var safeCall: SafeCall

    @Before
    fun setup() {
        safeCall = SafeCall
    }

    @Test
    fun `should return the value of T when execute function that return T`() =
        runTest {
            //given
            val execute = suspend { "just dummy string" }
            //when
            val result = safeCall(execute)
            //then
            assertThat(result).isInstanceOf(String::class.java)
        }

    @Test
    fun `should throw InvalidApiKeyException when execution throw ApiException with error code 7`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(7) }
            //when && then
            assertThrows<InvalidApiKeyException> { safeCall(execute) }
        }

    @Test
    fun `should throw PersonNotFoundException when execution throw ApiException with error code 34`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(34) }
            //when && then
            assertThrows<PersonNotFoundException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnauthorizedAccessException when execution throw ApiException with error code 401`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(401) }
            //when && then
            assertThrows<UnauthorizedAccessException> { safeCall(execute) }
        }

    @Test
    fun `should throw ForbiddenAccessException when execution throw ApiException with error code 403`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(403) }
            //when && then
            assertThrows<ForbiddenAccessException> { safeCall(execute) }
        }

    @Test
    fun `should throw RateLimitExceededException when execution throw ApiException with error code 429`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(429) }
            //when && then
            assertThrows<RateLimitExceededException> { safeCall(execute) }
        }

    @Test
    fun `should throw ServerErrorException when execution throw ApiException with error code (500 to 599)`() =
        runTest {
            //given
            val execute = suspend { throw ApiException((500..599).random()) }
            //when && then
            assertThrows<ServerErrorException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnknownException when execution throw ApiException with unexpected error code`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(0) }
            //when && then
            assertThrows<UnknownException> { safeCall(execute) }
        }

    @Test
    fun `should throw NetworkException when execution throw UnknownHostException`() =
        runTest {
            //given
            val execute = suspend { throw UnknownHostException() }
            //when && then
            assertThrows<NetworkException> { safeCall(execute) }
        }

    @Test
    fun `should throw NetworkException when execution throw SocketTimeoutException`() =
        runTest {
            //given
            val execute = suspend { throw SocketTimeoutException() }
            //when && then
            assertThrows<NetworkException> { safeCall(execute) }
        }

    @Test
    fun `should throw InvalidPersonIdException when execution throw SerializationException`() =
        runTest {
            //given
            val execute = suspend { throw SerializationException() }
            //when && then
            assertThrows<InvalidPersonIdException> { safeCall(execute) }
        }

    @Test
    fun `should throw InvalidPersonIdException when execution throw IllegalArgumentException`() =
        runTest {
            //given
            val execute = suspend { throw IllegalArgumentException() }
            //when && then
            assertThrows<InvalidPersonIdException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnknownException when execution throw unexpected exception`() =
        runTest {
            //given
            val execute = suspend { throw NullPointerException() }
            //when && then
            assertThrows<UnknownException> { safeCall(execute) }
        }
}