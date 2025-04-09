package com.wldnasyrf.ds.ui.animeList

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
class AnimeListViewModel @Inject constructor(
    private val repository: AnimeRepository): ViewModel() {
        fun getAnimeList(category: String?): LiveData<PagingData<AnimeData>> {
            return repository.getAnimeList(category).cachedIn(viewModelScope)
        }
    }