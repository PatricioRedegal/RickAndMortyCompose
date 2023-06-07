package com.news.data.remote

import com.news.domain.model.ApiError
import com.news.domain.util.StatusRequest
import com.squareup.moshi.Moshi
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): StatusRequest<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return StatusRequest.success(body)
            }

            runCatching {
                Moshi.Builder().build().adapter(ApiError::class.java).fromJson(response.errorBody()!!.source())
            }.onSuccess { apiError ->
                apiError!!.code = response.code()
                return error(apiError)
            }
            return error(ApiError())
        } catch (retrofitException: Exception) {
            return exception(retrofitException)
        }
    }

    private fun <T> error(apiError: ApiError): StatusRequest<T> {
        return StatusRequest.error(apiError)
    }

    private fun <T> exception(retrofitException: Exception): StatusRequest<T> {
        return StatusRequest.exception(retrofitException)
    }
}