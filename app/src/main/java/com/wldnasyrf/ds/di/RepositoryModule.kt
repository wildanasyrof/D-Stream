package com.wldnasyrf.ds.di

import com.wldnasyrf.ds.data.local.datastore.UserPreferences
import com.wldnasyrf.ds.data.local.room.database.FavoritesDao
import com.wldnasyrf.ds.data.remote.api.ApiService
import com.wldnasyrf.ds.data.repository.anime.AnimeRepository
import com.wldnasyrf.ds.data.repository.anime.AnimeRepositoryImpl
import com.wldnasyrf.ds.data.repository.user.UserRepository
import com.wldnasyrf.ds.data.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(apiService: ApiService, favoriteDao: FavoritesDao): AnimeRepository {
        return AnimeRepositoryImpl(apiService, favoriteDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService, userPreferences: UserPreferences): UserRepository {
        return UserRepositoryImpl(apiService, userPreferences)
    }
}