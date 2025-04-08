package com.wldnasyrf.ds.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.databinding.ItemBannerBinding

class BannerAdapter (private val animeList: List<AnimeData>,
                     private val onWatchClickListener: (AnimeData) -> Unit,
                     private val onDetailClickListener: (AnimeData) -> Unit
) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class  BannerViewHolder(private val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeData) {
            binding.tvTitle.text = anime.title
            Glide.with(binding.root.context)
                .load(anime.image_source)
                .into(binding.ivCarouselImage)

            binding.btnWatch.setOnClickListener {
                onWatchClickListener(anime)
            }

            binding.btnDetail.setOnClickListener {
                onDetailClickListener(anime)
            }
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