package com.giraffe.explore.screen


interface HasErrorMessage<T> {
    val errorMessage: String?
    fun withErrorMessage(msg: String): T
}
