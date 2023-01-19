package com.sara.giphygif.domain

sealed class ResponseState<T>(val list: T? = null, val message: String? = null) {
    class START<T> : ResponseState<T>()
    class LOADING<T> : ResponseState<T>()
    class SUCCESS<T>(list: T) : ResponseState<T>(list = list)
    class FAILURE<T>(message: String) : ResponseState<T>(message = message)
}