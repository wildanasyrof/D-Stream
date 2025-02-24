package com.wldnasyrf.ds.data.remote.api

import com.wldnasyrf.ds.data.remote.model.Anime
import com.wldnasyrf.ds.data.remote.model.AnimeDetail
import com.wldnasyrf.ds.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("anime")
    suspend fun getAnime(
        @Query("limit") size: Int,
        @Query("page") page: Int
    ): ApiResponse<Anime>

    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): ApiResponse<AnimeDetail>

}