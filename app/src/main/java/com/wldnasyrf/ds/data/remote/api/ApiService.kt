package com.wldnasyrf.ds.data.remote.api

import com.wldnasyrf.ds.data.remote.model.ApiResponse
import com.wldnasyrf.ds.data.remote.model.anime.Anime
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.data.remote.model.anime.Category
import com.wldnasyrf.ds.data.remote.model.anime.FavoriteRequest
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.data.remote.model.user.LoginResponse
import com.wldnasyrf.ds.data.remote.model.user.RegisterRequest
import com.wldnasyrf.ds.data.remote.model.user.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // User Service
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<User>

    // Anime Service
    @GET("anime")
    suspend fun getAnime(
        @Query("limit") size: Int? = 10,
        @Query("page") page: Int? = 1,
        @Query("title") title: String? = null,
        @Query("year") year: String? = null,
        @Query("studio") studio: String? = null,
        @Query("category") category: String? = null
    ): ApiResponse<Anime>

    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): ApiResponse<AnimeDetail>

    @GET("category")
    suspend fun getCategory() : ApiResponse<List<Category>>

    @POST("user/favorite")
    suspend fun addFavorite(@Body request: FavoriteRequest): ApiResponse<Nothing>

    @DELETE("user/favorite/{id}")
    suspend fun deleteFavorite(@Path("id") id: Int): ApiResponse<Nothing>

}