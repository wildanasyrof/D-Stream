package com.wldnasyrf.ds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wldnasyrf.ds.R
import com.wldnasyrf.ds.data.remote.model.Episode
import com.wldnasyrf.ds.databinding.ItemsEpisodeBinding

class EpisodeAdapter(
    private val onItemClick: (Episode) -> Unit
) : ListAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemsEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        holder.bind(episode)
    }

    inner class EpisodeViewHolder(private val binding: ItemsEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            binding.tvEpisodeName.text = episode.title
            binding.tvEpisodeNumber.text = "Episode ${episode.episodeNumber}"

            // Assuming episode has an image URL
            Glide.with(binding.ivEpisode.context)
                .load(episode.videoUrl)
                .placeholder(R.drawable.klenteng)
                .into(binding.ivEpisode)

            binding.root.setOnClickListener { onItemClick(episode) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }
}
