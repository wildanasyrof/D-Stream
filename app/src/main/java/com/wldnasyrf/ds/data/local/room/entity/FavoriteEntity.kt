package com.wldnasyrf.ds.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val altTitles: String,
    val year: String,
    val rating: Int,
    val imageSource: String,
)