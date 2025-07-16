package com.giraffe.media.exception

// Base
open class MediaDomainException : Exception()

// Common
class NetworkDomainException : MediaDomainException()
class UnknownDomainException : MediaDomainException()
class InvalidApiKeyDomainException : MediaDomainException()

// Auth-related
class UnauthorizedDomainException : MediaDomainException()
class ForbiddenDomainException : MediaDomainException()
class RateLimitedDomainException : MediaDomainException()

// Not Found
class NotFoundDomainException : MediaDomainException()

// Validation
class ValidationDomainException : MediaDomainException()

// Server
class ServerErrorDomainException : MediaDomainException()

// Local
class CorruptDbDomainException : MediaDomainException()
class DiskErrorDomainException : MediaDomainException()

// Network specific
class TimeoutDomainException : MediaDomainException()
class RedirectedDomainException : MediaDomainException()
class ClientErrorDomainException : MediaDomainException()
