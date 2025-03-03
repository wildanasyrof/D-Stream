package com.wldnasyrf.ds.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.data.repository.anime.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: AnimeRepository): ViewModel(){

    val anime: LiveData<PagingData<AnimeData>> = repository.getAnimeList().cachedIn(viewModelScope)
}