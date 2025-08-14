package com.giraffe.user.usecase

import com.giraffe.user.exception.EmptyUsernameException
import com.giraffe.user.exception.InvalidPasswordException
import com.giraffe.user.exception.InvalidUsernameMatchException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class ValidateAuthInputUseCaseTest {
    private val validateAuthInputUseCase: ValidateAuthInputUseCase =
        ValidateAuthInputUseCase()

    @Test
    fun `validateUsername should throw EmptyUsernameException when username is blank`() {
        assertThrows<EmptyUsernameException> {
            validateAuthInputUseCase.validateUsername("   ")
        }
    }

    @Test
    fun `validateUsername should throw InvalidUsernameMatchException for invalid characters`() {
        assertThrows<InvalidUsernameMatchException> {
            validateAuthInputUseCase.validateUsername("user@name!")
        }
    }

    @Test
    fun `validateUsername should not throw exception for valid username`() {
        assertDoesNotThrow {
            validateAuthInputUseCase.validateUsername("valid-user_123")
        }
    }

    @Test
    fun `validatePassword should throw InvalidPasswordException when password is too short`() {
        assertThrows<InvalidPasswordException> {
            validateAuthInputUseCase.validatePassword("123")
        }
    }

    @Test
    fun `validatePassword should not throw exception for valid password`() {
        assertDoesNotThrow {
            validateAuthInputUseCase.validatePassword("1234")
        }
    }
}