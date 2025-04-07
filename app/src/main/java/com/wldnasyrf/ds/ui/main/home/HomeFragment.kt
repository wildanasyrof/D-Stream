package com.wldnasyrf.ds.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wldnasyrf.ds.adapter.HomeAdapter
import com.wldnasyrf.ds.adapter.HomeLoadStateAdapter
import com.wldnasyrf.ds.adapter.HomePagingAdapter
import com.wldnasyrf.ds.data.remote.model.ApiResponse
import com.wldnasyrf.ds.data.remote.model.anime.Anime
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.databinding.FragmentHomeBinding
import com.wldnasyrf.ds.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import com.wldnasyrf.ds.utils.Result

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomePagingAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var trendingAdapter: HomeAdapter
    private lateinit var ongoingAdapter: HomeAdapter
    private lateinit var recommendedAdapter: HomeAdapter
    private lateinit var bannerAdapter: BannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setupRecyclerView()
        setupRecyclerViews()
        observeCategory()
        observeAnimeList()
    }

    private fun observeCategory() {
        viewModel.animeRecommended.observe(viewLifecycleOwner, Observer { result ->
            handleAnimeResult(result, recommendedAdapter)
        })
        viewModel.category.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Error -> {

                }
                is Result.Loading -> {

                }
                is Result.Success -> {
                    val listCategory = result.data.data
                    categoryAdapter.submitList(listCategory)
                }
            }

        })
    }

    private fun setupRecyclerViews() {

        categoryAdapter = CategoryAdapter { catId ->
            // Handle click and open DetailActivity
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("ANIME_ID", catId)
            startActivity(intent)
        }

        trendingAdapter = HomeAdapter { animeId ->
            // Handle click and open DetailActivity
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("ANIME_ID", animeId)
            startActivity(intent)
        }
        ongoingAdapter = HomeAdapter { animeId ->
            // Handle click and open DetailActivity
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("ANIME_ID", animeId)
            startActivity(intent)
        }
        recommendedAdapter = HomeAdapter { animeId ->
            // Handle click and open DetailActivity
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("ANIME_ID", animeId)
            startActivity(intent)
        }

        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        binding.rvTrending.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }

        binding.rvOngoing.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ongoingAdapter
        }

        binding.rvRecomended.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendedAdapter
        }

        binding.vpBanner.apply {
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
        }
    }


//    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: HomeAdapter) {
//        recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.adapter = adapter
//    }

    private fun observeAnimeList() {
//        viewModel.anime.observe(viewLifecycleOwner) {
//            homeAdapter.submitData(lifecycle, it)
//        }

        viewModel.animeTrending.observe(viewLifecycleOwner, Observer { result ->
            handleAnimeResult(result, trendingAdapter)
            if (result is Result.Success) {
                setupBanner(result.data.data?.data!!)
            }
        })

        viewModel.animeOnGoing.observe(viewLifecycleOwner, Observer { result ->
            handleAnimeResult(result, ongoingAdapter)
        })

        viewModel.animeRecommended.observe(viewLifecycleOwner, Observer { result ->
            handleAnimeResult(result, recommendedAdapter)
        })
    }

    private fun handleAnimeResult(result: Result<ApiResponse<Anime>>, adapter: HomeAdapter) {
        when (result) {
            is Result.Loading -> {
//                binding.progressBar.visibility = View.VISIBLE
            }
            is Result.Success -> {
//                binding.progressBar.visibility = View.GONE
                val animeList = result.data.data?.data // Assuming response has a list
                adapter.submitList(animeList)
            }
            is Result.Error -> {
//                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupBanner(animeList: List<AnimeData>) {
        bannerAdapter = BannerAdapter(animeList)
        binding.vpBanner.adapter = bannerAdapter
    }
//
//    private fun setupRecyclerView() {
//        homeAdapter = HomePagingAdapter { animeId ->
//            // Handle click and open DetailActivity
//            val intent = Intent(requireContext(), DetailActivity::class.java)
//            intent.putExtra("ANIME_ID", animeId)
//            startActivity(intent)
//        }
//
//        homeAdapter.addLoadStateListener { loadState ->
//            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
//            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
//            binding.retryButton.setOnClickListener { homeAdapter.retry() }
//        }
//
//        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = homeAdapter.withLoadStateHeaderAndFooter(
//                header = HomeLoadStateAdapter { homeAdapter.retry() },
//                footer = HomeLoadStateAdapter { homeAdapter.retry() }
//            )
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}