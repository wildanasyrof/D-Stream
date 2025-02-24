package com.wldnasyrf.ds.data.repository.anime

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wldnasyrf.ds.data.remote.model.AnimeData
import com.wldnasyrf.ds.data.remote.model.AnimeDetail
import com.wldnasyrf.ds.utils.Result

interface AnimeRepository {
    fun getAnimeList(): LiveData<PagingData<AnimeData>>
    fun getAnimeDetail(id: Int): LiveData<Result<AnimeDetail>>
}