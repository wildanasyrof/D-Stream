package com.wldnasyrf.ds.ui.register

import androidx.lifecycle.ViewModel
import com.wldnasyrf.ds.data.remote.model.user.RegisterRequest
import com.wldnasyrf.ds.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun register(request: RegisterRequest) = repository.register(request)
}