package com.wldnasyrf.ds.di

import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.repository.anime.AnimeRepository
import com.wldnasyrf.ds.data.repository.anime.AnimeRepositoryImpl
import com.wldnasyrf.ds.data.repository.user.UserRepository
import com.wldnasyrf.ds.data.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://go-api.wldnasyrf.my.id/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeRepository(apiService: ApiService): AnimeRepository {
        return AnimeRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }
}