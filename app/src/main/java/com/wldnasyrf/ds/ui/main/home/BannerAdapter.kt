package com.wldnasyrf.ds.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.databinding.ItemBannerBinding

class BannerAdapter (private val animeList: List<AnimeData>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    class  BannerViewHolder(private val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {
            Glide.with(binding.root.context)
                .load(anime.image_source)
                .into(binding.ivCarouselImage)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BannerViewHolder,
        position: Int
    ) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int = animeList.size
}