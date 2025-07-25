package com.giraffe.user.exception

open class AuthException: Exception()

class InvalidCredentialsException : AuthException()
class TokenCreationException : AuthException()
class TokenValidationException : AuthException()
class SessionCreationException : AuthException()
class InvalidPasswordException : AuthException()
class EmptyUsernameException : AuthException()
class InvalidUsernameOrPasswordException : AuthException()
class UnknownException() : AuthException()