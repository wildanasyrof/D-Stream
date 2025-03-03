package com.wldnasyrf.ds.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.data.repository.anime.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.wldnasyrf.ds.utils.Result


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _animeDetail = MutableLiveData<Result<AnimeDetail>>()
    val animeDetail: LiveData<Result<AnimeDetail>> get() = _animeDetail

    fun fetchAnimeDetail(id: Int) {
        Log.e("DetailViewModel", "fetchAnimeDetail called with ID: $id") // 🔍 Debug log

        _animeDetail.value = Result.Loading // Show loading state

        animeRepository.getAnimeDetail(id).observeForever { result ->
            Log.e("DetailViewModel", "Repository returned: $result") // 🔍 Debug response
            _animeDetail.value = result
        }
    }

}