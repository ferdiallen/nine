package com.example.nineintelligence.domain.util

sealed class Resource<T>(private val data: T? = null, private val errorMessage: String? = null) {
    class Success<T>(val data: T?) : Resource<T>(data)
    class Loading<T>(val data: T?) : Resource<T>(data)
    data class Error<T>(val errorMessages: String? = null) :
        Resource<T>(null, errorMessages)

    class Empty<T> : Resource<T>(null)
}
