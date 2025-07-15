package com.giraffe.media.explore.exceptions

open class ExploreException: Exception()

//local
class NotFoundException: ExploreException()
class ValidationException: ExploreException()

//remote
class NoInternetException: ExploreException()
class RequestTimeoutException: ExploreException()
class TooManyRequestsException: ExploreException()
class ServerException: ExploreException()
class UnrecognizableDataException: ExploreException()

//unknown
class UnknownException: ExploreException()