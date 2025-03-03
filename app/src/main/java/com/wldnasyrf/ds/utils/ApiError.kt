package com.wldnasyrf.ds.utils

import com.google.gson.Gson
import com.wldnasyrf.ds.data.remote.model.ApiResponse
import retrofit2.HttpException

object ApiError {
    fun parseError(exception: HttpException) : ApiResponse<*> {
        return try {
            val errorBody = exception.response()?.errorBody()?.string()
            Gson().fromJson(errorBody, ApiResponse::class.java)
        } catch (e: Exception) {
            ApiResponse(status = "error", message = "Unknown Api Error", error = e.localizedMessage, data = null)
        }
    }
}