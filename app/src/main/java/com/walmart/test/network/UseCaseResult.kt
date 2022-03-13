package com.walmart.test.network


sealed class UseCaseResult<out T : Any> {

    data class Success<out T : Any>(val nasa: T? = null) : UseCaseResult<T>()
    class Error(val exception: Throwable?) : UseCaseResult<Nothing>()
}