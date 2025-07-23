package com.giraffe.media.util

import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.ApiDataException
import com.giraffe.media.exception.NoInternetDataException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.media.utils.SafeCall
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

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
    fun `should throw NotFoundDomainException when execution throw ApiException with error code 34`() =
        runTest {
            val execute = suspend { throw ApiDataException(34) }
            assertThrows<NotFoundException> { safeCall(execute) }
        }

    @Test
    fun `should throw AccessDeniedDomainException when execution throw ApiException with error code 38`() =
        runTest {
            val execute = suspend { throw ApiDataException(38) }
            assertThrows<AccessDeniedException> { safeCall(execute) }
        }

    @Test
    fun `should throw AccessDeniedDomainException when execution throw ApiException with error code 25`() =
        runTest {
            val execute = suspend { throw ApiDataException(25) }
            assertThrows<AccessDeniedException> { safeCall(execute) }
        }


    @Test
    fun `should throw ValidationDomainException when execution throw ApiException with error code 5`() =
        runTest {
            val execute = suspend { throw ApiDataException(5) }
            assertThrows<ValidationException> { safeCall(execute) }
        }



    @Test
    fun `should throw UnknownDomainException when execution throw ApiException with unexpected error code`() =
        runTest {
            val execute = suspend { throw ApiDataException(999) }
            assertThrows<UnknownException> { safeCall(execute) }
        }

    @Test
    fun `should throw NetworkDomainException when execution throw NoInternetException`() =
        runTest {
            val execute = suspend { throw NoInternetDataException() }
            assertThrows<NoInternetException> { safeCall(execute) }
        }

    @Test
    fun `should throw ValidationDomainException when execution throw SerializationException`() =
        runTest {
            val execute = suspend { throw SerializationException() }
            assertThrows<ValidationException> { safeCall(execute) }
        }

    @Test
    fun `should throw ValidationDomainException when execution throw IllegalArgumentException`() =
        runTest {
            val execute = suspend { throw IllegalArgumentException() }
            assertThrows<ValidationException> { safeCall(execute) }
        }

    @Test
    fun `should throw UnknownDomainException when execution throw unexpected exception`() =
        runTest {
            val execute = suspend { throw NullPointerException() }
            assertThrows<UnknownException> { safeCall(execute) }
        }
}
