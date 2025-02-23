package com.wldnasyrf.ds.data.remote.model

data class ApiResponse<T> (
    val message: String,
    val status: String,
    val `data`: T? = null,
    val error: Any? = null
    )