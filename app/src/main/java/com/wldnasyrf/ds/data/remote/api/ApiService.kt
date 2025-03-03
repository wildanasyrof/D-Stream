package com.wldnasyrf.ds.data.remote.api

import com.wldnasyrf.ds.data.remote.model.ApiResponse
import com.wldnasyrf.ds.data.remote.model.anime.Anime
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.data.remote.model.user.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // User Service
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

    // Anime Service
    @GET("anime")
    suspend fun getAnime(
        @Query("limit") size: Int,
        @Query("page") page: Int
    ): ApiResponse<Anime>

    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): ApiResponse<AnimeDetail>

}