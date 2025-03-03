package com.wldnasyrf.ds.data.repository.anime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.wldnasyrf.ds.data.paging.AnimePagingSource
import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.utils.Result
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

    override fun getAnimeDetail(id: Int): LiveData<Result<AnimeDetail>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getAnimeDetail(id)
            Log.e("AnimeRepositoryImpl", "Full API Response: $response") // 🔍 Debug full response

            if (response.data != null) {
                emit(Result.Success(response.data))
            } else {
                Log.e("AnimeRepositoryImpl", "API returned null data: ${response.message}")
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            Log.e("AnimeRepositoryImpl", "Network error: ${e.localizedMessage}")
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }


}