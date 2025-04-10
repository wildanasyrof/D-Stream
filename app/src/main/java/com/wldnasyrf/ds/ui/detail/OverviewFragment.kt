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
import com.google.android.flexbox.FlexboxLayout
import com.wldnasyrf.ds.R
import com.wldnasyrf.ds.data.remote.model.anime.AnimeDetail
import com.wldnasyrf.ds.databinding.FragmentOverviewBinding
import com.wldnasyrf.ds.ui.customView.CategoryChipView
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
        observeFavoriteState()
    }

    private fun observeFavoriteState() {
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) {
                binding.btnFavourite.setImageResource(R.drawable.ic_favourite)
            } else {
                binding.btnFavourite.setImageResource(R.drawable.ic_favourite_border)
            }
        }
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
//            tvStudio.text = studio
            tvYear.text = year
            tvRating.text = rating.toString()
            tvDescription.text = synopsis

            categories.forEach { category ->
                val chip = CategoryChipView(requireContext()).apply {
                    text = category.name
                }

                // Set layout params for FlexboxLayout
                val layoutParams = FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    (this as? ViewGroup.MarginLayoutParams)?.apply {
                        marginStart = resources.getDimensionPixelSize(R.dimen.spacing_xs)
                        marginEnd = resources.getDimensionPixelSize(R.dimen.spacing_xs)
                        topMargin = resources.getDimensionPixelSize(R.dimen.spacing_xs)
                        bottomMargin = resources.getDimensionPixelSize(R.dimen.spacing_xs)
                    }
                }

                chip.layoutParams = layoutParams
                categoriesContainer.addView(chip)
            }
        }

        val episodeFragment = EpisodeFragment.newInstance(animeDetail.id, animeDetail.title)

        btnWatchNow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, episodeFragment)
                .commit()
        }

        btnFavourite.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (viewModel.isFavorite.value == true) {
                    // If already favorite, remove it
                    val errorMessage = viewModel.deleteFavorite(animeDetail)
                    if (errorMessage != null) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // If not favorite, add it
                    val errorMessage = viewModel.addFavorite(animeDetail)
                    if (errorMessage != null) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                // Refresh favorite state
                viewModel.getIsFavorite(animeDetail.id)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding to avoid memory leaks
    }
}