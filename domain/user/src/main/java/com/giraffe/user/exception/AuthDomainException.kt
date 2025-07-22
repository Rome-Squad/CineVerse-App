package com.giraffe.user.exception

sealed class AuthDomainException: Exception()

class InvalidCredentialsDomainException :
    AuthDomainException()

class TokenCreationDomainException :
    AuthDomainException()

class TokenValidationDomainException :
    AuthDomainException()

class SessionCreationDomainException :
    AuthDomainException()

class GuestSessionCreationDomainException :
    AuthDomainException()


class UnknownException() :
    AuthDomainException()