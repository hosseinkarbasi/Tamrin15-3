package com.example.exoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.exoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.*
import com.google.common.collect.ImmutableList

class MainActivity : AppCompatActivity(), Player.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupPlayer()
        addMP4Files()

        if (savedInstanceState != null) {
            if (savedInstanceState.getInt("mediaItem") != 0) {
                val restoredMediaItem = savedInstanceState.getInt("mediaItem")
                val seekTime = savedInstanceState.getLong("SeekTime")
                player.seekTo(restoredMediaItem, seekTime)
                player.play()
            }
        }
    }

    private fun addMP4Files() {
        val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4_1))
        val mediaItem2 = MediaItem.fromUri(getString(R.string.media_url_mp4_2))
        val newItems: List<MediaItem> = ImmutableList.of(
            mediaItem,
            mediaItem2
        )
        player.addMediaItems(newItems)
        player.prepare()
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        player.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun onResume() {
        super.onResume()
        setupPlayer()
        addMP4Files()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MainActivity", "onSaveInstanceState: " + player.currentPosition)
        outState.putLong("SeekTime", player.currentPosition)
        outState.putInt("mediaItem", player.currentMediaItemIndex)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onSaveInstanceState: " + player.currentPosition)
    }
}