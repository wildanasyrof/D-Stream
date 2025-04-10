package com.wldnasyrf.ds.data.repository.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.wldnasyrf.ds.data.local.datastore.UserPreferences
import com.wldnasyrf.ds.data.local.datastore.model.UserPreferencesModel
import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.data.remote.model.user.LoginResponse
import com.wldnasyrf.ds.data.remote.model.user.RegisterRequest
import com.wldnasyrf.ds.data.remote.model.user.User
import com.wldnasyrf.ds.utils.ApiError
import com.wldnasyrf.ds.utils.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) : UserRepository {
    override fun login(request: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(request)
            Log.i("UserRepository", "Login success: $response")

            if (response.data != null) {
                saveToken(response.data.token)
                emit(Result.Success(response.data))
            }
        } catch (e: HttpException) {
            val errorResponse = ApiError.parseError(e)
            Log.e("UserRepositoryImpl", "HttpException: ${errorResponse.error}")
            emit(Result.Error(errorResponse.error.toString()))
        } catch (e: Throwable) {
            Log.e("UserRepositoryImpl", "Network error: $e")
        }
    }

    override fun register(request: RegisterRequest): LiveData<Result<User>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.register(request)
            Log.i("UserRepository", "Login success: $response")

            if (response.data != null) {
                emit(Result.Success(response.data))
            }
        } catch (e: HttpException) {
            Log.e("UserRepositoryImpl", "Error Code: ${e.code()}")
            val errorResponse = ApiError.parseError(e)
            Log.e("UserRepositoryImpl", "HttpException: ${errorResponse.error}")
            emit(Result.Error(errorResponse.error.toString()))
        } catch (e: Throwable) {
            Log.e("UserRepositoryImpl", "Network error: $e")
        }
    }

    override suspend fun saveToken(token: String) {
        userPreferences.saveUserPreferences(UserPreferencesModel(token=token))
    }

    override fun getUserPreferences(): Flow<UserPreferencesModel> {
        return userPreferences.getUserPreferences()
    }

    override suspend fun clearUserPreferences() {
        userPreferences.clearUserPreferences()
    }
}