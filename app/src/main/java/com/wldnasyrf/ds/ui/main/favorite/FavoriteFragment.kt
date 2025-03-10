package com.wldnasyrf.ds.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.wldnasyrf.ds.adapter.FavoriteAdapter
import com.wldnasyrf.ds.databinding.FragmentFavoriteBinding
import com.wldnasyrf.ds.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavoriteList()
    }

    private fun observeFavoriteList() {
        viewModel.favorites.observe(viewLifecycleOwner) {
            favoriteAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter { anime ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("ANIME_ID", anime.id)
            startActivity(intent)
        }

        binding.rvFavorite.layoutManager = GridLayoutManager(requireContext(), 2) // 3 columns
        binding.rvFavorite.adapter = favoriteAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}