package com.giraffe.media.exception

open class MediaDataException : Exception()

class NoInternetDataException : MediaDataException()
class RequestTimeoutDataException : MediaDataException()
class TooManyRequestsDataException : MediaDataException()
class ServerDataException : MediaDataException()
class RedirectedDataException : MediaDataException()
class ClientErrorDataException : MediaDataException()
class SerializationDataException : MediaDataException()
class ApiDataException(val code: Int) : MediaDataException()

class InvalidApiKeyDataException : MediaDataException()
class UnauthorizedAccessDataException : MediaDataException()
class ForbiddenAccessDataException : MediaDataException()
class RateLimitExceededDataException : MediaDataException()

class NotFoundDataException : MediaDataException()
class InvalidIdDataException : MediaDataException()
class CorruptDatabaseDataException : MediaDataException()
class DiskAccessDataException : MediaDataException()

class UnknownNetworkDataException : MediaDataException()