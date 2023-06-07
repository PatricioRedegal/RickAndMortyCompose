package com.news.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiError(
    var code: Int = 0,
    val message: String = "",
    val detail: String = ""
)