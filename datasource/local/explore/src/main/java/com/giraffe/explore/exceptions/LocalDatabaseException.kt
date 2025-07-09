package com.giraffe.explore.exceptions

open class LocalDatabaseException: Exception()

class NotFoundException: LocalDatabaseException()
class ValidationException: LocalDatabaseException()

class UnKnownLocalDatabaseException: LocalDatabaseException()