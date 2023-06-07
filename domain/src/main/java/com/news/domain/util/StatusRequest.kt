package com.news.domain.util

import com.news.domain.model.ApiError

data class StatusRequest<T>(
    var status: Status,
    var data: T? = null,
    val apiError: ApiError? = null,
    val exception: Throwable? = null)
{
    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T): StatusRequest<T> = StatusRequest(
            Status.SUCCESS,
            data)

        fun <T> error(apiError: ApiError): StatusRequest<T> = StatusRequest(
            Status.ERROR,
            apiError = apiError)

        fun <T> exception(exception: Exception): StatusRequest<T> = StatusRequest(
            Status.ERROR,
            apiError = ApiError(0, exception.message ?: "", exception.message ?: "Error"),
            exception = exception)
    }
}

fun StatusRequest.Status.isSuccessful() = this == StatusRequest.Status.SUCCESS
fun StatusRequest.Status.isFailure() = this != StatusRequest.Status.SUCCESS
