package com.giraffe.media.util


import com.giraffe.media.exception.ForbiddenDomainException
import com.giraffe.media.exception.InvalidApiKeyDomainException
import com.giraffe.media.exception.NetworkDomainException
import com.giraffe.media.exception.NotFoundDomainException
import com.giraffe.media.exception.RateLimitedDomainException
import com.giraffe.media.exception.ServerErrorDomainException
import com.giraffe.media.exception.UnauthorizedDomainException
import com.giraffe.media.exception.UnknownDomainException
import com.giraffe.media.exception.ValidationDomainException
import com.giraffe.media.person.util.ApiException
import com.giraffe.media.utils.SafeCall
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
    fun `should throw InvalidApiKeyDomainException when execution throw ApiException with error code 7`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(7) }
            //when && then
            assertThrows<InvalidApiKeyDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw NotFoundDomainException when execution throw ApiException with error code 34`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(34) }
            //when && then
            assertThrows<NotFoundDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnauthorizedDomainException when execution throw ApiException with error code 401`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(401) }
            //when && then
            assertThrows<UnauthorizedDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw ForbiddenDomainException when execution throw ApiException with error code 403`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(403) }
            //when && then
            assertThrows<ForbiddenDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw RateLimitedDomainException when execution throw ApiException with error code 429`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(429) }
            //when && then
            assertThrows<RateLimitedDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw ServerErrorDomainException when execution throw ApiException with error code (500 to 599)`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(501) }
            //when && then
            assertThrows<ServerErrorDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnknownDomainException when execution throw ApiException with unexpected error code`() =
        runTest {
            //given
            val execute = suspend { throw ApiException(0) }
            //when && then
            assertThrows<UnknownDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw NetworkDomainException when execution throw UnknownHostException`() =
        runTest {
            //given
            val execute = suspend { throw UnknownHostException() }
            //when && then
            assertThrows<NetworkDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw NetworkDomainException when execution throw SocketTimeoutException`() =
        runTest {
            //given
            val execute = suspend { throw SocketTimeoutException() }
            //when && then
            assertThrows<NetworkDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw ValidationDomainException when execution throw SerializationException`() =
        runTest {
            //given
            val execute = suspend { throw SerializationException() }
            //when && then
            assertThrows<ValidationDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw ValidationDomainException when execution throw IllegalArgumentException`() =
        runTest {
            //given
            val execute = suspend { throw IllegalArgumentException() }
            //when && then
            assertThrows<ValidationDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnknownDomainException when execution throw unexpected exception`() =
        runTest {
            //given
            val execute = suspend { throw NullPointerException() }
            //when && then
            assertThrows<UnknownDomainException> { safeCall(execute) }
        }
}