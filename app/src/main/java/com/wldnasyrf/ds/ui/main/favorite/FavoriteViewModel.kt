package com.wldnasyrf.ds.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wldnasyrf.ds.data.local.room.entity.FavoriteEntity
import com.wldnasyrf.ds.data.repository.anime.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    animeRepository: AnimeRepository
) : ViewModel() {

    val favorites: LiveData<List<FavoriteEntity>> = animeRepository.getAllFavorites()
}