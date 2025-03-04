package com.wldnasyrf.ds.data.remote.model.anime

import com.google.gson.annotations.SerializedName

data class FavoriteRequest (
    @SerializedName("anime_id")
    val id: Int,
)