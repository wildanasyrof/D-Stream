package com.wldnasyrf.ds.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wldnasyrf.ds.adapter.HomeAdapter
import com.wldnasyrf.ds.data.remote.model.anime.AnimeData
import com.wldnasyrf.ds.databinding.FragmentHomeBinding
import com.wldnasyrf.ds.ui.animeList.AnimeListActivity
import com.wldnasyrf.ds.ui.detail.DetailActivity
import com.wldnasyrf.ds.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var trendingAdapter: HomeAdapter
    private lateinit var ongoingAdapter: HomeAdapter
    private lateinit var recommendedAdapter: HomeAdapter
    private lateinit var bannerAdapter: BannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        setupRecyclerViews()
        observeCategoryData()
        observeAnimeSections()
        setupClickListeners()
    }

    private fun initAdapters() {
        categoryAdapter = CategoryAdapter { category ->
            navigateToAnimeList(category.name)
        }

        trendingAdapter = createAnimeAdapter()
        ongoingAdapter = createAnimeAdapter()
        recommendedAdapter = createAnimeAdapter()
    }

    private fun setupRecyclerViews() {
        binding.rvCategory.setupHorizontal(categoryAdapter)
        binding.rvTrending.setupHorizontal(trendingAdapter)
        binding.rvOngoing.setupHorizontal(ongoingAdapter)
        binding.rvRecommended.setupHorizontal(recommendedAdapter)

        binding.vpBanner.apply {
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
        }
    }

    private fun observeCategoryData() {
        viewModel.category.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> categoryAdapter.submitList(result.data.data)
                else -> {} // Handle loading/error if needed
            }
        }
    }

    private fun observeAnimeSections() {
        observeAnimeByCategory("Action", trendingAdapter, true)
        observeAnimeByCategory("Fantasy", ongoingAdapter)
        observeAnimeByCategory("Drama", recommendedAdapter)
    }

    private fun observeAnimeByCategory(
        category: String,
        adapter: HomeAdapter,
        setupBanner: Boolean = false
    ) {
        viewModel.getAnimeList(category).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val animeList = result.data.data?.data.orEmpty()
                    adapter.submitList(animeList)
                    if (setupBanner) initBanner(animeList)
                }
                is Result.Error -> showToast(result.message)
                is Result.Loading -> {} // Optional: show loading state
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnSearch.setOnClickListener {
            navigateToAnimeList("Trending")
        }
    }

    private fun navigateToAnimeList(query: String) {
        val intent = Intent(requireContext(), AnimeListActivity::class.java).apply {
            putExtra("QUERY", query)
        }
        startActivity(intent)
    }

    private fun createAnimeAdapter(): HomeAdapter {
        return HomeAdapter { animeId ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("ANIME_ID", animeId)
            }
            startActivity(intent)
        }
    }

    private fun initBanner(animeList: List<AnimeData>) {
        bannerAdapter = BannerAdapter(
            animeList = animeList,
            onWatchClickListener = { anime -> navigateToDetail(anime.id, fragmentId = 1) },
            onDetailClickListener = { anime -> navigateToDetail(anime.id) }
        )
        binding.vpBanner.adapter = bannerAdapter
    }

    private fun navigateToDetail(animeId: Int, fragmentId: Int? = null) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("ANIME_ID", animeId)
            fragmentId?.let { putExtra("FRAGMENT_ID", it) }
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun RecyclerView.setupHorizontal(adapter: RecyclerView.Adapter<*>) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        this.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}
