package com.wldnasyrf.ds.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wldnasyrf.ds.data.local.datastore.UserPreferences
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.data.remote.model.anime.FavoriteRequest
import com.wldnasyrf.ds.data.repository.anime.AnimeRepository
import com.wldnasyrf.ds.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _animeDetail = MutableLiveData<Result<AnimeDetail>>()
    val animeDetail: LiveData<Result<AnimeDetail>> get() = _animeDetail

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun fetchAnimeDetail(id: Int) {
        Log.e("DetailViewModel", "fetchAnimeDetail called with ID: $id") // 🔍 Debug log

        _animeDetail.value = Result.Loading // Show loading state

        animeRepository.getAnimeDetail(id).observeForever { result ->
            Log.e("DetailViewModel", "Repository returned: $result") // 🔍 Debug response
            _animeDetail.value = result
        }
    }

    suspend fun addFavorite(anime: AnimeDetail) : String? {
        val token = userPreferences.getUserPreferences().firstOrNull()?.token

        if (!token.isNullOrEmpty()) {
            val response = animeRepository.addFavoriteApi(FavoriteRequest(anime.id))

            if ( response.status != "error") {
                animeRepository.addFavoriteRoom(anime)
                return null
            } else {
                return response.message
            }
        } else {
            animeRepository.addFavoriteRoom(anime)
            return null
        }
    }

    suspend fun deleteFavorite(anime: AnimeDetail) : String? {
        val token = userPreferences.getUserPreferences().firstOrNull()?.token

        if (!token.isNullOrEmpty()) {
            val response = animeRepository.deleteFavoriteApi(anime.id)

            if ( response.status != "error") {
                animeRepository.deleteFavoriteRoom(anime)
                return null
            } else {
                return response.message
            }
        } else {
            animeRepository.deleteFavoriteRoom(anime)
            return null
        }
    }

    fun getIsFavorite(animeId: Int) {
        viewModelScope.launch {
            val favorite = animeRepository.getFavoriteByID(animeId)
            _isFavorite.postValue(favorite != null) // ✅ If not null, it's a favorite
        }
    }

}