package com.wldnasyrf.ds.data.repository.user

import androidx.lifecycle.LiveData
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.data.remote.model.user.LoginResponse
import com.wldnasyrf.ds.utils.Result

interface UserRepository {
    fun login(request: LoginRequest): LiveData<Result<LoginResponse>>
}