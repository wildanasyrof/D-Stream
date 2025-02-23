package com.wldnasyrf.ds.data.remote.model

data class Anime(
    val limit: Int,
    val page: Int,
    val total: Int,
    val `data`: List<AnimeData>
)

data class AnimeData(
    val alt_titles: String,
    val categories: List<Category>,
    val chapters: String,
    val episode_count: Int,
    val id: Int,
    val image_source: String,
    val rating: Double,
    val studio: String,
    val synopsis: String,
    val title: String,
    val year: String
)
