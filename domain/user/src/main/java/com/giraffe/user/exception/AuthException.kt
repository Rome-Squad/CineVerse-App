package com.giraffe.user.exception

open class AuthException: Exception()
class InvalidLoginException(message: String) : AuthException()
class InvalidPasswordException : AuthException()
class EmptyUsernameException : AuthException()
class InvalidUsernameOrPasswordException : AuthException()
class UnknownException() : AuthException()