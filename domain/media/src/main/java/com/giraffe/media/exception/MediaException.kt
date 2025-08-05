package com.giraffe.media.exception

open class MediaException(message: String = "") : Exception(message)

class NoInternetException : MediaException()
class AccessDeniedException : MediaException()
class ValidationException(message: String = "") : MediaException(message)
class NotFoundException : MediaException()
class UnknownException : MediaException()
