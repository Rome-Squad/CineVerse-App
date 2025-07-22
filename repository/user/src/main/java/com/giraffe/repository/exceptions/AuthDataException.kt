package com.giraffe.repository.exceptions

open class AuthDataException: Exception()

class InvalidCredentialsException : AuthDataException()
class TokenCreationException : AuthDataException()
class TokenValidationException : AuthDataException()
class SessionCreationException : AuthDataException()
class GuestSessionCreationException : AuthDataException()

