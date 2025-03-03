package com.wldnasyrf.ds.ui.login

import androidx.lifecycle.ViewModel
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun login(request: LoginRequest) =  repository.login(request)
}