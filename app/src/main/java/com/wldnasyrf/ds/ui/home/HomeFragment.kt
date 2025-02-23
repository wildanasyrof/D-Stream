package com.wldnasyrf.ds.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.wldnasyrf.ds.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = HomePagingAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter.withLoadStateHeaderAndFooter(
                header = HomeLoadStateAdapter { homeAdapter.retry() },
                footer = HomeLoadStateAdapter { homeAdapter.retry() }
            )
        }

        viewModel.anime.observe(viewLifecycleOwner) {
            homeAdapter.submitData(lifecycle, it)
        }

        homeAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            binding.retryButton.setOnClickListener { homeAdapter.retry() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}