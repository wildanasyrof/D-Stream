package com.wldnasyrf.ds.data.repository.anime

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wldnasyrf.ds.data.remote.model.AnimeData

interface AnimeRepository {
    fun getAnimeList(): LiveData<PagingData<AnimeData>>
}