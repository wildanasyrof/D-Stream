package com.wldnasyrf.ds.data.local.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wldnasyrf.ds.data.local.room.entity.FavoriteEntity

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites WHERE id = :animeId LIMIT 1")
    suspend fun getFavoriteById(animeId: Int): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE id = :animeId")
    suspend fun deleteByAnimeId(animeId: Int)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<FavoriteEntity>>
}