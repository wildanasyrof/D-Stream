package com.wldnasyrf.ds.data.repository.anime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.wldnasyrf.ds.data.local.room.database.FavoritesDao
import com.wldnasyrf.ds.data.local.room.entity.FavoriteEntity
import com.wldnasyrf.ds.data.paging.AnimePagingSource
import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.remote.model.ApiResponse
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.data.remote.model.anime.FavoriteRequest
import com.wldnasyrf.ds.utils.ApiError
import com.wldnasyrf.ds.utils.Result
import retrofit2.HttpException
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoritesDao
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

    override suspend fun addFavoriteApi(request: FavoriteRequest) : ApiResponse<Nothing> {
        return try {
            val response = apiService.addFavorite(request)
            Log.i("AnimeRepository", "Add Favorite success: $response")
            ApiResponse(status = response.status, message = response.message)
        } catch (e: HttpException) {
            val errorResponse = ApiError.parseError(e)
            Log.e("AnimeRepository", "Add Favorite error: ${errorResponse.error}")
            ApiResponse(status = "error", message = errorResponse.message)
        } catch (e: Throwable) {
            Log.e("AnimeRepository", "Network error: $e")
            ApiResponse(status = "error", message = e.localizedMessage?.toString() ?: "Network Error!")
        }
    }

    override suspend fun addFavoriteRoom(anime: AnimeDetail) {
        favoriteDao.insert(FavoriteEntity(
            id = anime.id,
            title = anime.title,
            altTitles = anime.altTitles,
            year = anime.year,
            rating = anime.rating.toInt(),
            imageSource = anime.imageSource
        ))
    }

    override suspend fun getFavoriteByID(animeId: Int): FavoriteEntity? {
        return favoriteDao.getFavoriteById(animeId)
    }

    override suspend fun deleteFavoriteApi(animeId: Int): ApiResponse<Nothing> {
        return try {
            val response = apiService.deleteFavorite(animeId)
            Log.i("AnimeRepository", "Remove Favorite success: $response")
            ApiResponse(status = response.status, message = response.message)
        } catch (e: HttpException) {
            val errorResponse = ApiError.parseError(e)
            Log.e("AnimeRepository", "Remove Favorite error: ${errorResponse.error}")
            ApiResponse(status = "error", message = errorResponse.message)
        } catch (e: Throwable) {
            Log.e("AnimeRepository", "Network error: $e")
            ApiResponse(status = "error", message = e.localizedMessage?.toString() ?: "Network Error!")
        }
    }

    override suspend fun deleteFavoriteRoom(anime: AnimeDetail) {
        return favoriteDao.deleteByAnimeId(anime.id)
    }

    override fun getAllFavorites(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorites()
    }
}