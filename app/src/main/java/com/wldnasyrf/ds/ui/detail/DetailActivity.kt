package com.wldnasyrf.ds.ui.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wldnasyrf.ds.R
import com.wldnasyrf.ds.data.remote.model.AnimeDetail
import com.wldnasyrf.ds.databinding.ActivityDetailBinding
import com.wldnasyrf.ds.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val animeId = intent.getIntExtra("ANIME_ID", 0)
        Log.e("ANIME_ID", "Data: $animeId")

        setupView()
        observeAnimeDetail(animeId)
        fragmentSetup(savedInstanceState)
    }

    private fun setupView() {
        val toolbar = binding.toolbarId
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val marginTop = resources.getDimensionPixelSize(R.dimen.toolbar_margin_top)
            val toolbarLayoutParams = toolbar.layoutParams as ViewGroup.MarginLayoutParams
            toolbarLayoutParams.topMargin = marginTop
            toolbar.layoutParams = toolbarLayoutParams
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = ""
    }

    private fun fragmentSetup(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = OverviewFragment() // Your fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    private fun observeAnimeDetail(animeId: Int) {
        Log.e("DetailActivity", "observeAnimeDetail CALLED with ID: $animeId") // 🔍 Debug log

        viewModel.fetchAnimeDetail(animeId)

        viewModel.animeDetail.observe(this) { result ->
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

    private fun bindView(animeDetail: AnimeDetail) {

        // Load Blurred Background
        Glide.with(this)
            .load(animeDetail.imageSource)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3))) // Apply blur effect
            .into(binding.ivAnimeBg)

        // Load Foreground Image (Not Cropped)
        Glide.with(this)
            .load(animeDetail.imageSource)
            .fitCenter() // Ensure it fits inside without cropping
            .into(binding.ivAnimeImage)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}