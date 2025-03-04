package com.wldnasyrf.ds.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wldnasyrf.ds.data.local.datastore.model.UserPreferencesModel
import com.wldnasyrf.ds.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val userPreferences: LiveData<UserPreferencesModel> = userRepository.getUserPreferences().asLiveData()

    fun logout() {
        viewModelScope.launch {
            userRepository.clearUserPreferences()
        }
    }

}