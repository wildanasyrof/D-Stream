package com.wldnasyrf.ds.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wldnasyrf.ds.data.remote.model.ApiResponse
import com.wldnasyrf.ds.data.remote.model.anime.Anime
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.data.repository.anime.AnimeRepository
import com.wldnasyrf.ds.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: AnimeRepository): ViewModel(){

    val anime: LiveData<PagingData<AnimeData>> = repository.getAnimeList().cachedIn(viewModelScope)
    val animeTrending: LiveData<Result<ApiResponse<Anime>>> = repository.getAnimeData()
    val animeOnGoing: LiveData<Result<ApiResponse<Anime>>> = repository.getAnimeData()
    val animeRecomended: LiveData<Result<ApiResponse<Anime>>> = repository.getAnimeData()
}