package com.wldnasyrf.ds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.databinding.ItemAnimeVerticalBinding

class AnimePagingAdapter(
    private val onItemClick: (Int) -> Unit
) : PagingDataAdapter<AnimeData, AnimePagingAdapter.AnimeViewHolder>(DIFF_CALLBACK) {

    inner class AnimeViewHolder(private val binding: ItemAnimeVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {
            with(binding) {
                tvTitle.text = anime.title
                tvAltTitle.text = anime.alt_titles
                tvRatingCount.text = anime.rating.toString()
                tvChapters.text = anime.year
                tvEpisode.text = anime.episode_count.toString()

                // Load blurred background image
                Glide.with(ivBackground)
                    .load(anime.image_source)
                    .into(ivBackground)

                // Load poster image with rounded corners
                Glide.with(ivPoster)
                    .load(anime.image_source)
                    .apply(RequestOptions().transform(RoundedCorners(16)))
                    .into(ivPoster)

                // Handle item click
                root.setOnClickListener {
                    onItemClick(anime.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(
            ItemAnimeVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        getItem(position)?.let { anime ->
            holder.bind(anime)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnimeData>() {
            override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean {
                return oldItem.id == newItem.id // Better to use ID than title for uniqueness
            }

            override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean {
                return oldItem == newItem
            }
        }
    }
}