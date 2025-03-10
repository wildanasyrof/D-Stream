package com.wldnasyrf.ds.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wldnasyrf.ds.data.local.room.database.FavoritesDao
import com.wldnasyrf.ds.data.local.room.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class DsDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}