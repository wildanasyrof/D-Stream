package com.wldnasyrf.ds.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wldnasyrf.ds.data.remote.model.AnimeData
import com.wldnasyrf.ds.databinding.ItemAnimeBinding

class HomePagingAdapter : PagingDataAdapter<AnimeData, HomePagingAdapter.HomeViewHolder>(DIFF_CALLBACK){

    inner class HomeViewHolder(private val binding: ItemAnimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {
            binding.textTitle.text = anime.title
            binding.textAltTitles.text = anime.alt_titles
            binding.textRating.text = anime.rating.toString()
            binding.textEpisodes.text = anime.episode_count.toString()
            binding.textYear.text = anime.year
            Glide.with(binding.imageAnime).load(anime.image_source).into(binding.imageAnime)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePagingAdapter.HomeViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePagingAdapter.HomeViewHolder, position: Int) {
        val anime = getItem(position)
        anime?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnimeData>() {
            override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean {
                return oldItem == newItem
            }
        }
    }

}