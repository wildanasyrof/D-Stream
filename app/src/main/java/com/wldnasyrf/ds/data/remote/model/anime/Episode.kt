package com.wldnasyrf.ds.data.remote.model.anime

import com.google.gson.annotations.SerializedName

data class Episode(
    val id: Int,

    @SerializedName("anime_id")
    val animeId: Int,

    @SerializedName("episode_number")
    val episodeNumber: Int,

    val title: String,

    @SerializedName("video_url")
    val videoUrl: String
)
