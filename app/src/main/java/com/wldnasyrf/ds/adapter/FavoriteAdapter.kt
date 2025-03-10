package com.wldnasyrf.ds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wldnasyrf.ds.data.local.room.entity.FavoriteEntity
import com.wldnasyrf.ds.databinding.ItemFavoriteBinding
import jp.wasabeef.glide.transformations.BlurTransformation

class FavoriteAdapter(
    private val onItemClick: (FavoriteEntity) -> Unit
) : ListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: FavoriteEntity) {
            binding.tvTitle.text = anime.title
            binding.tvEpisode.text = anime.altTitles

            // Load Blurred Background
            Glide.with(binding.ivAnimeBg)
                .load(anime.imageSource)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .into(binding.ivAnimeBg)

            // Load Foreground Image
            Glide.with(binding.ivAnimeImage)
                .load(anime.imageSource)
                .apply(RequestOptions().transform(RoundedCorners(16)))
                .fitCenter()
                .into(binding.ivAnimeImage)

            // Handle item click
            binding.root.setOnClickListener {
                onItemClick(anime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
