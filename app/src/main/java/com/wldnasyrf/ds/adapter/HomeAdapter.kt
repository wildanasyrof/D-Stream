package com.wldnasyrf.ds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.databinding.ItemAnimeHomeBinding

class HomeAdapter : ListAdapter<AnimeData, HomeAdapter.AnimeViewHolder>(AnimeDiffCallback()) {

    class AnimeViewHolder(private val binding: ItemAnimeHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {
            // Load image using Glide
            Glide.with(binding.root.context)
                .load(anime.image_source)
                .into(binding.ivPlace)

            binding.root.setOnClickListener {
                // Handle item click if needed
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

// DiffUtil for efficient updates
class AnimeDiffCallback : DiffUtil.ItemCallback<AnimeData>() {
    override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean {
        return oldItem == newItem
    }
}
