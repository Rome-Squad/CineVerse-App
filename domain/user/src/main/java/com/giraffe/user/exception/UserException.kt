package com.giraffe.user.exception

open class UserException : Exception()
class InvalidLoginException() : UserException()
class InvalidPasswordException : UserException()
class EmptyUsernameException : UserException()
class InvalidUsernameMatchException : UserException()
class InvalidUsernameOrPasswordException : UserException()
class UnknownException() : UserException()

class FailedToGetUserName() : UserException()

class NoInternetException : UserException()
class AccessDeniedException : UserException()
