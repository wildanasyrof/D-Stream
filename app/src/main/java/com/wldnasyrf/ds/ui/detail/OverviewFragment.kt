package com.wldnasyrf.ds.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.wldnasyrf.ds.R
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.data.remote.model.anime.FavoriteRequest
import com.wldnasyrf.ds.databinding.FragmentOverviewBinding
import com.wldnasyrf.ds.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //code here

        observeAnimeData()
    }

    private fun observeAnimeData() {
        viewModel.animeDetail.observe(viewLifecycleOwner) { result ->
            Log.e("OverviewFragment", "Result: $result") // 🔍 Debug log
            when (result) {
                is Result.Loading -> {
                    // Show loading
                }
                is Result.Success -> {
                    val animeDetail = result.data
                    bindView(animeDetail)
                    // Update UI with animeDetail
                }
                is Result.Error -> {
                    // Show error
                }
            }
        }
    }

    private fun bindView(animeDetail: AnimeDetail) = with(binding) {
        animeDetail.apply {
            tvTitle.text = title
            tvAltTitle.text = altTitles
            tvChapters.text = chapters
            tvStudio.text = studio
            tvYear.text = year
            tvRating.text = rating.toString()
            tvDescription.text = synopsis
        }

        val episodeFragment = EpisodeFragment.newInstance(animeDetail.id, animeDetail.title)

        btnWatchNow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, episodeFragment)
                .commit()
        }

        btnFavourite.setOnClickListener {
            lifecycleScope.launch {
                val errorMessage = viewModel.addFavoriteApi(FavoriteRequest(animeDetail.id))
                if (errorMessage != null) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding to avoid memory leaks
    }
}