package com.giraffe.user.exception

open class AuthException: Exception()
class InvalidLoginException(message: String) : AuthException()
class InvalidPasswordException : AuthException()
class EmptyUsernameException : AuthException()
class InvalidUsernameMatchException : AuthException()
class InvalidUsernameOrPasswordException : AuthException()
class UnknownException(message: String = "An unknown error occurred.") : AuthException()