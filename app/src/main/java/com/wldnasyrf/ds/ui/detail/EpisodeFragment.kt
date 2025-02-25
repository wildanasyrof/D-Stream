package com.wldnasyrf.ds.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wldnasyrf.ds.R
import com.wldnasyrf.ds.utils.Result
import com.wldnasyrf.ds.adapter.EpisodeAdapter
import com.wldnasyrf.ds.databinding.FragmentEpisodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var episodeAdapter: EpisodeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeAnimeData()
        setupToolbar()
    }

    private fun setupToolbar() {

        val animeTitle = arguments?.getString("anime_title") ?: "Episode"

        val toolbar = binding.toolbar
        toolbar.title = animeTitle
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OverviewFragment())
                .commit()
        }
    }

    private fun observeAnimeData() {
        viewModel.animeDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val episodes = result.data.episodes // Assuming AnimeDetail has a list of episodes
                    episodeAdapter.submitList(episodes)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        episodeAdapter = EpisodeAdapter { episode ->
            Toast.makeText(requireContext(), "Clicked: ${episode.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvEpisode.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = episodeAdapter
        }
    }

    companion object {
        fun newInstance(animeId: Int, animeTitle: String): EpisodeFragment {
            return EpisodeFragment().apply {
                arguments = Bundle().apply {
                    putInt("anime_id", animeId)
                    putString("anime_title", animeTitle)
                }
            }
        }
    }
}