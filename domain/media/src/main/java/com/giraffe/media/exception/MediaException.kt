package com.giraffe.media.exception

open class MediaDomainException : Exception()

class NetworkDomainException : MediaDomainException()
class TimeoutDomainException : MediaDomainException()
class ServerErrorDomainException : MediaDomainException()

class UnauthorizedDomainException : MediaDomainException()
class AccessDeniedDomainException : MediaDomainException()

class ValidationDomainException : MediaDomainException()
class InvalidRequestMethodDomainException : MediaDomainException()

class NotFoundDomainException : MediaDomainException()

class UnknownDomainException : MediaDomainException()
