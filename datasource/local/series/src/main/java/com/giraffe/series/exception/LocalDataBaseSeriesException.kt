package com.giraffe.series.exception

open class LocalDatabaseSeriesException: Exception()

class NotFoundException():LocalDatabaseSeriesException()
class ValidationException():LocalDatabaseSeriesException()
class CorruptDatabaseException():LocalDatabaseSeriesException()
class DiskAccessException():LocalDatabaseSeriesException()
class UnknownLocalDatabaseException():LocalDatabaseSeriesException()