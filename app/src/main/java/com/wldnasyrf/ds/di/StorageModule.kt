package com.wldnasyrf.ds.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.wldnasyrf.ds.data.local.room.database.FavoritesDao
import com.wldnasyrf.ds.data.local.room.DsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) : DsDatabase {
        return Room.databaseBuilder(
            context,
            DsDatabase::class.java,
            "ds_database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(database: DsDatabase): FavoritesDao {
        return database.favoritesDao()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
