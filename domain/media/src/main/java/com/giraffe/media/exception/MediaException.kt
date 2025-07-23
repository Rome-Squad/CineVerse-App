package com.giraffe.media.exception

open class MediaException : Exception()

class NetworkException : MediaException()
class TimeoutException : MediaException()
class ServerErrorException : MediaException()

class UnauthorizedException : MediaException()
class AccessDeniedException : MediaException()

class ValidationException : MediaException()
class InvalidRequestMethodException : MediaException()

class NotFoundException : MediaException()
class InfrastructureException : MediaException()

class UnknownException : MediaException()
