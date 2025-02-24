package com.wldnasyrf.ds.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wldnasyrf.ds.R
import com.wldnasyrf.ds.data.remote.model.AnimeDetail
import com.wldnasyrf.ds.databinding.FragmentOverviewBinding
import com.wldnasyrf.ds.utils.Result
import dagger.hilt.android.AndroidEntryPoint

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

        switchFragment()
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
    }

    private fun switchFragment() {
        binding.btnWatchNow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EpisodeFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding to avoid memory leaks
    }
}