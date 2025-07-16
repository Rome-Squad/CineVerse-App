package com.giraffe.media.util

import com.giraffe.media.exception.AccessDeniedDomainException
import com.giraffe.media.exception.ApiException
import com.giraffe.media.exception.InvalidRequestMethodDomainException
import com.giraffe.media.exception.NetworkDomainException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundDomainException
import com.giraffe.media.exception.ServerErrorDomainException
import com.giraffe.media.exception.TimeoutDomainException
import com.giraffe.media.exception.UnauthorizedDomainException
import com.giraffe.media.exception.UnknownDomainException
import com.giraffe.media.exception.ValidationDomainException
import com.giraffe.media.utils.SafeCall
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.net.SocketTimeoutException

class SafeCallTest {
    private lateinit var safeCall: SafeCall

    @Before
    fun setup() {
        safeCall = SafeCall
    }

    @Test
    fun `should return the value of T when execute function that return T`() =
        runTest {
            val execute = suspend { "just dummy string" }
            val result = safeCall(execute)
            assertThat(result).isInstanceOf(String::class.java)
        }

    @Test
    fun `should throw InvalidApiKeyDomainException when execution throw ApiException with error code 7`() =
        runTest {
            val execute = suspend { throw ApiException(7) }
            assertThrows<UnauthorizedDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw NotFoundDomainException when execution throw ApiException with error code 34`() =
        runTest {
            val execute = suspend { throw ApiException(34) }
            assertThrows<NotFoundDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnauthorizedDomainException when execution throw ApiException with error code 3`() =
        runTest {
            val execute = suspend { throw ApiException(3) }
            assertThrows<UnauthorizedDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw AccessDeniedDomainException when execution throw ApiException with error code 38`() =
        runTest {
            val execute = suspend { throw ApiException(38) }
            assertThrows<AccessDeniedDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw AccessDeniedDomainException when execution throw ApiException with error code 25`() =
        runTest {
            val execute = suspend { throw ApiException(25) }
            assertThrows<AccessDeniedDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw ServerErrorDomainException when execution throw ApiException with error code 15`() =
        runTest {
            val execute = suspend { throw ApiException(15) }
            assertThrows<ServerErrorDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw InvalidRequestMethodDomainException when execution throw ApiException with error code 4`() =
        runTest {
            val execute = suspend { throw ApiException(4) }
            assertThrows<InvalidRequestMethodDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw ValidationDomainException when execution throw ApiException with error code 5`() =
        runTest {
            val execute = suspend { throw ApiException(5) }
            assertThrows<ValidationDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw TimeoutDomainException when execution throw ApiException with error code 24`() =
        runTest {
            val execute = suspend { throw ApiException(24) }
            assertThrows<TimeoutDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnknownDomainException when execution throw ApiException with unexpected error code`() =
        runTest {
            val execute = suspend { throw ApiException(999) }
            assertThrows<UnknownDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw TimeoutDomainException when execution throw SocketTimeoutException`() =
        runTest {
            val execute = suspend { throw SocketTimeoutException() }
            assertThrows<TimeoutDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw NetworkDomainException when execution throw NoInternetException`() =
        runTest {
            val execute = suspend { throw NoInternetException() }
            assertThrows<NetworkDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw ValidationDomainException when execution throw SerializationException`() =
        runTest {
            val execute = suspend { throw SerializationException() }
            assertThrows<ValidationDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw ValidationDomainException when execution throw IllegalArgumentException`() =
        runTest {
            val execute = suspend { throw IllegalArgumentException() }
            assertThrows<ValidationDomainException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnknownDomainException when execution throw unexpected exception`() =
        runTest {
            val execute = suspend { throw NullPointerException() }
            assertThrows<UnknownDomainException> { safeCall(execute) }
        }
}
