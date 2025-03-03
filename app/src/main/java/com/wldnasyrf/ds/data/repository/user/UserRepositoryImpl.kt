package com.wldnasyrf.ds.data.repository.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.data.remote.model.user.LoginResponse
import com.wldnasyrf.ds.utils.ApiError
import com.wldnasyrf.ds.utils.Result
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override fun login(request: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(request)
            Log.i("UserRepositoryImpl", response.toString())

            Log.i("UserRepository", "Login success: $response")
        } catch (e: HttpException) {
            val errorResponse = ApiError.parseError(e)
            Log.e("UserRepositoryImpl", "HttpException: ${errorResponse.error}")
            emit(Result.Error(errorResponse.error.toString()))
        } catch (e: Throwable) {
            Log.e("UserRepositoryImpl", "Network error: $e")
        }
    }
}