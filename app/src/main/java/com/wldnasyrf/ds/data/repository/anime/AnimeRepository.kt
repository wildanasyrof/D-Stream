package com.wldnasyrf.ds.data.repository.anime

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wldnasyrf.ds.data.local.room.entity.FavoriteEntity
import com.wldnasyrf.ds.data.remote.model.ApiResponse
import com.wldnasyrf.ds.data.remote.model.anime.Anime
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.data.remote.model.anime.Category
import com.wldnasyrf.ds.data.remote.model.anime.FavoriteRequest
import com.wldnasyrf.ds.utils.Result

interface AnimeRepository {
    fun getCategory(): LiveData<Result<ApiResponse<List<Category>>>>
    fun getAnimeList(category: String? = null): LiveData<PagingData<AnimeData>>
    fun getAnimeDetail(id: Int): LiveData<Result<AnimeDetail>>
    fun getAnimeData(category: String? = null): LiveData<Result<ApiResponse<Anime>>>
    suspend fun addFavoriteApi(request: FavoriteRequest): ApiResponse<Nothing>
    suspend fun addFavoriteRoom(anime: AnimeDetail)
    suspend fun getFavoriteByID(animeId: Int) : FavoriteEntity?
    suspend fun deleteFavoriteApi(animeId: Int): ApiResponse<Nothing>
    suspend fun deleteFavoriteRoom(anime: AnimeDetail)
    fun getAllFavorites(): LiveData<List<FavoriteEntity>>
}