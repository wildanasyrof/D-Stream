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
import com.wldnasyrf.ds.databinding.ItemAnimeVerticalBinding
import jp.wasabeef.glide.transformations.BlurTransformation

class FavoriteAdapter(
    private val onItemClick: (FavoriteEntity) -> Unit
) : ListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    inner class FavoriteViewHolder(private val binding: ItemAnimeVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: FavoriteEntity) {
            with(binding) {
                tvTitle.text = anime.title
                tvAltTitle.text = anime.altTitles
                tvRatingCount.text = anime.rating.toString()
                tvChapters.text = anime.year

                // Load blurred background image
                Glide.with(ivBackground)
                    .load(anime.imageSource)
                    .into(ivBackground)

                // Load poster image with rounded corners
                Glide.with(ivPoster)
                    .load(anime.imageSource)
                    .apply(RequestOptions().transform(RoundedCorners(16)))
                    .into(ivPoster)

                // Handle item click
                root.setOnClickListener {
                    onItemClick(anime)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemAnimeVerticalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
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