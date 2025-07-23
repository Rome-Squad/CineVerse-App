package com.giraffe.user.exception

open class AuthException: Exception()

class InvalidCredentialsException :
    AuthException()

class TokenCreationException :
    AuthException()

class TokenValidationException :
    AuthException()

class SessionCreationException :
    AuthException()

class GuestSessionCreationException :
    AuthException()

class InvalidPasswordException :
    AuthException()

class InvalidEmailException :
    AuthException()

class EmptyUsernameException :
        AuthException()


class UnknownException() :
    AuthException()