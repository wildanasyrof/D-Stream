package com.wldnasyrf.ds.data.repository.user

import androidx.lifecycle.LiveData
import com.wldnasyrf.ds.data.local.datastore.model.UserPreferencesModel
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.data.remote.model.user.LoginResponse
import com.wldnasyrf.ds.utils.Result
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun login(request: LoginRequest): LiveData<Result<LoginResponse>>
    suspend fun saveToken(token: String)
    fun getUserPreferences(): Flow<UserPreferencesModel>
    suspend fun clearUserPreferences()
}