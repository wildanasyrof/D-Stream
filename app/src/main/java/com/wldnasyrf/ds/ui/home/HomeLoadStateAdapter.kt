package com.wldnasyrf.ds.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wldnasyrf.ds.databinding.PagingLoadStateBinding

class HomeLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<HomeLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(private val binding: PagingLoadStateBinding, private val retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.errorMessage.isVisible = false
                    binding.retryButton.isVisible = false
                }
                is LoadState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.errorMessage.isVisible = true // ✅ Show error message
                    binding.retryButton.isVisible = true // ✅ Show retry button
                    binding.errorMessage.text = loadState.error.localizedMessage ?: "Something went wrong"
                }
                is LoadState.NotLoading -> {
                    binding.progressBar.isVisible = false
                    binding.errorMessage.isVisible = false
                    binding.retryButton.isVisible = false
                }
            }

            binding.retryButton.setOnClickListener { retry() }
        }
    }
    override fun onBindViewHolder(
        holder: HomeLoadStateAdapter.LoadStateViewHolder,
        loadState: LoadState
    ) {
       holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HomeLoadStateAdapter.LoadStateViewHolder {
        val binding = PagingLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding, retry)
    }

}