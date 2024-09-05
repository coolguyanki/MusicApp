package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicapp.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var exoPlayer: ExoPlayer

    var playerListener = object : Player.Listener{
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            showGif(isPlaying)
        }
    }

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyExoPlayer.getCurrentSong()?.apply {
          binding.songTitleTv.text = title
            binding.songSubTitleTv.text = subTitle

            Glide.with(binding.songCoverIv).load(coverUrl)
                .circleCrop()
                .into(binding.songCoverIv)

            Glide.with(binding.songGifIv).load(R.drawable.ic_media_playing)
                .circleCrop()
                .into(binding.songGifIv)

             exoPlayer = MyExoPlayer.getInstance()!!
            binding.playerView.player = exoPlayer
            binding.playerView.showController()
            exoPlayer.addListener(playerListener)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.removeListener(playerListener)
    }

    fun showGif(show: Boolean) {
        if (show) {
            binding.songGifIv.visibility = View.VISIBLE
        } else {
            binding.songGifIv.visibility = View.INVISIBLE
        }

    }

}