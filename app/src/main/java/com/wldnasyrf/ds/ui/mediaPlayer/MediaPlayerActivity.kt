package com.wldnasyrf.ds.ui.mediaPlayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.wldnasyrf.ds.R
import com.wldnasyrf.ds.databinding.ActivityMediaPlayerBinding
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

@UnstableApi
class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaPlayerBinding
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private var areControlsVisible = true
    private var isVideoPaused = false

    companion object {
        private const val DEFAULT_VIDEO_URL = "https://www.trenggalekkab.go.id/uploads/video/202101131610507322.mp4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializePlayer()
        setupControls()
        hideSystemUI()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .apply {
                playWhenReady = this@MediaPlayerActivity.playWhenReady
                seekTo(currentWindow, playbackPosition)
                setMediaItem(MediaItem.fromUri(intent.getStringExtra("VIDEO_URL") ?: DEFAULT_VIDEO_URL))
                prepare()

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_READY) {
                            updateProgress()
                            binding.playPauseButton.setImageResource(
                                if (isPlaying) R.drawable.ic_pause_media else R.drawable.ic_play_media
                            )
                        }
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        isVideoPaused = !isPlaying
                        binding.playPauseButton.setImageResource(
                            if (isPlaying) R.drawable.ic_pause_media else R.drawable.ic_play_media
                        )

                        if (isVideoPaused) {
                            showControls()
                        }

                        if (isPlaying) {
                            startProgressUpdate()
                            // Auto-hide controls after 3 seconds when playing
                            binding.playerView.postDelayed({
                                if (areControlsVisible) toggleControls()
                            }, 3000)
                        } else {
                            stopProgressUpdate()
                        }
                    }
                })
            }

        binding.playerView.player = player
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupControls() {
        binding.videoTitle.text = intent.getStringExtra("VIDEO_TITLE") ?: ""

        binding.closeButton.setOnClickListener { finish() }

        binding.userButton.setOnClickListener { /* Handle user action */ }

        binding.playPauseButton.setOnClickListener {
            player?.let {
                if (it.isPlaying) {
                    it.pause()
                    binding.playPauseButton.setImageResource(R.drawable.ic_play_media)
                    isVideoPaused = true
                    showControls()
                } else {
                    it.play()
                    binding.playPauseButton.setImageResource(R.drawable.ic_pause_media)
                    isVideoPaused = false
                    // Auto-hide controls after 3 seconds when playing
                    binding.playerView.postDelayed({
                        if (areControlsVisible) toggleControls()
                    }, 3000)
                }
            }
        }

        binding.forwardButton.setOnClickListener {
            player?.let {
                val newPosition = it.currentPosition + 10000
                it.seekTo(newPosition.coerceAtMost(it.duration))
            }
        }

        binding.rewindButton.setOnClickListener {
            player?.let {
                val newPosition = it.currentPosition - 10000
                it.seekTo(newPosition.coerceAtLeast(0))
            }
        }

        binding.timeBar.addListener(object : androidx.media3.ui.TimeBar.OnScrubListener {
            override fun onScrubStart(timeBar: androidx.media3.ui.TimeBar, position: Long) {
                stopProgressUpdate()
            }

            override fun onScrubMove(timeBar: androidx.media3.ui.TimeBar, position: Long) {
                binding.currentTime.text = formatTime(position)
                player?.let { exoPlayer ->
                    binding.totalTime.text = "-${formatTime(exoPlayer.duration - position)}"
                }
            }

            override fun onScrubStop(timeBar: androidx.media3.ui.TimeBar, position: Long, canceled: Boolean) {
                player?.seekTo(position)
                startProgressUpdate()
            }
        })

        binding.playerView.setOnClickListener { toggleControls() }
    }

    private fun toggleControls() {
        if (isVideoPaused) {
            showControls()
            return
        }

        areControlsVisible = !areControlsVisible
        val visibility = if (areControlsVisible) View.VISIBLE else View.INVISIBLE
        val alpha = if (areControlsVisible) 1f else 0f

        binding.topControls.visibility = visibility
        binding.centerControls.visibility = visibility
        binding.bottomControls.visibility = visibility

        binding.topControls.animate().alpha(alpha).setDuration(300).start()
        binding.centerControls.animate().alpha(alpha).setDuration(300).start()
        binding.bottomControls.animate().alpha(alpha).setDuration(300).start()

        if (areControlsVisible && player?.isPlaying == true) {
            binding.playerView.postDelayed({
                if (areControlsVisible && !isVideoPaused) toggleControls()
            }, 3000)
        }
    }

    private fun showControls() {
        if (!areControlsVisible) {
            areControlsVisible = true
            binding.topControls.visibility = View.VISIBLE
            binding.centerControls.visibility = View.VISIBLE
            binding.bottomControls.visibility = View.VISIBLE

            binding.topControls.animate().alpha(1f).setDuration(300).start()
            binding.centerControls.animate().alpha(1f).setDuration(300).start()
            binding.bottomControls.animate().alpha(1f).setDuration(300).start()
        }
    }

    private fun startProgressUpdate() {
        binding.playerView.post(progressUpdateRunnable)
    }

    private fun stopProgressUpdate() {
        binding.playerView.removeCallbacks(progressUpdateRunnable)
    }

    private val progressUpdateRunnable = object : Runnable {
        override fun run() {
            updateProgress()
            binding.playerView.postDelayed(this, 1000)
        }
    }

    private fun updateProgress() {
        player?.let {
            val duration = it.duration
            val position = it.currentPosition
            val remainingTime = duration - position

            binding.currentTime.text = formatTime(position)
            binding.totalTime.text = "-${formatTime(remainingTime)}" // Show remaining time with minus sign
            binding.timeBar.setDuration(duration)
            binding.timeBar.setPosition(position)
            binding.timeBar.setBufferedPosition(it.bufferedPosition)
        }
    }

    private fun formatTime(ms: Long): String {
        if (ms <= 0) return "00:00"

        val duration = ms.milliseconds
        val hours = duration.inWholeHours
        val minutes = duration.inWholeMinutes % 60
        val seconds = duration.inWholeSeconds % 60

        return if (hours > 0) {
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onStart() {
        super.onStart()
        if (player == null) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        if (player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        player?.let {
            playbackPosition = it.currentPosition
            currentWindow = it.currentMediaItemIndex
            playWhenReady = it.playWhenReady
        }
        stopProgressUpdate()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            currentWindow = it.currentMediaItemIndex
            playWhenReady = it.playWhenReady
            it.release()
        }
        player = null
    }
}