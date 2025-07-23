package com.giraffe.media.exception

open class MediaException : Exception()

class NoInternetException : MediaException()
class UnauthorizedException : MediaException()
class AccessDeniedException : MediaException()
class ValidationException : MediaException()
class NotFoundException : MediaException()
class UnknownException : MediaException()
