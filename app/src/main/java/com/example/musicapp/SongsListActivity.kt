package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicapp.adapter.SongListAdapter
import com.example.musicapp.databinding.ActivitySongsListBinding
import com.example.musicapp.models.CategoryModel

class SongsListActivity : AppCompatActivity() {

    companion object {
         lateinit var category: CategoryModel
    }

    private lateinit var binding: ActivitySongsListBinding
    private lateinit var songsListAdapter: SongListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.nameTextView.text = category.name

            Glide.with(binding.coverImageView).load(category.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                ).into(binding.coverImageView)

        setUpSongRecyclerView()

    }

    fun setUpSongRecyclerView() {
        songsListAdapter = SongListAdapter(category.songs)
        binding.songsRv.layoutManager = LinearLayoutManager(this)
        binding.songsRv.adapter = songsListAdapter

    }

}