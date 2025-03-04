package com.wldnasyrf.ds.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wldnasyrf.ds.data.local.datastore.model.UserPreferencesModel
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val userPreferences: LiveData<UserPreferencesModel> = repository.getUserPreferences().asLiveData()
    fun login(request: LoginRequest) =  repository.login(request)
}