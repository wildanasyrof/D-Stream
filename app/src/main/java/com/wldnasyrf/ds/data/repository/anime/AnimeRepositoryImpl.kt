package com.wldnasyrf.ds.data.repository.anime

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.wldnasyrf.ds.data.paging.AnimePagingSource
import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.remote.model.AnimeData
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AnimeRepository {
    override fun getAnimeList(): LiveData<PagingData<AnimeData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                AnimePagingSource(apiService)
            }
        ).liveData
    }
}