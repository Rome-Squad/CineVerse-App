package com.giraffe.media.movies.exception

open class MoviesException : Exception()

class  NetworkError : MoviesException()

class  InvalidApiKey : MoviesException()

class  NotFoundError : MoviesException()

class  ServerError : MoviesException()

class  UnknownError : MoviesException()