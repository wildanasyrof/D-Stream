package com.wldnasyrf.ds.data.remote.model.anime

import com.google.gson.annotations.SerializedName

data class AnimeDetail(
    val id: Int,
    val title: String,

    @SerializedName("alt_titles")
    val altTitles: String,

    val chapters: String,
    val studio: String,
    val year: String,
    val rating: Double,
    val synopsis: String,

    @SerializedName("image_source")
    val imageSource: String,

    val categories: List<Category>,
    val episodes: List<Episode>
)
