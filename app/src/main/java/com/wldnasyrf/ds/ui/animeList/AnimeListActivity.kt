package com.wldnasyrf.ds.ui.animeList

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.wldnasyrf.ds.adapter.AnimePagingAdapter
import com.wldnasyrf.ds.databinding.ActivityAnimeListBinding
import com.wldnasyrf.ds.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimeListBinding
    private val viewModel: AnimeListViewModel by viewModels()
    private lateinit var animeAdapter: AnimePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val query = intent.getStringExtra("QUERY")

        setupToolbar(query)
        setupRecyclerView()
        observeData(query)
    }

    private fun setupToolbar(string: String?) {
        val toolbar = binding.toolbarId

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = string
    }

    private fun setupRecyclerView() {
        animeAdapter = AnimePagingAdapter { animeId ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ANIME_ID", animeId)
            startActivity(intent)
        }

        binding.rvAnime.layoutManager = LinearLayoutManager(this)
        binding.rvAnime.adapter = animeAdapter
    }

    private fun observeData(query: String?) {
        viewModel.getAnimeList(query).observe(this) { result ->
            animeAdapter.submitData(lifecycle, result)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}