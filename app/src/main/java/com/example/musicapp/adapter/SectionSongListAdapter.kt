package com.example.musicapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicapp.MyExoPlayer
import com.example.musicapp.PlayerActivity
import com.example.musicapp.databinding.SectionSongListRecyclerRowBinding
import com.example.musicapp.databinding.SongsListItemLayoutBinding
import com.example.musicapp.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore


class SectionSongListAdapter(private val songIdList: List<String>) :
    RecyclerView.Adapter<SectionSongListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: SectionSongListRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(songId: String) {
            binding.songTitleTv.text = songId

            FirebaseFirestore.getInstance().collection("songs")
                .document(songId).get()
                .addOnSuccessListener {
                    val song = it.toObject(SongModel::class.java)
                    song?.apply {
                        binding.songTitleTv.text = title
                        binding.songSubtitleTv.text = subTitle

                        Glide.with(binding.ivSongCover).load(coverUrl)
                            .apply(
                                RequestOptions().transform(RoundedCorners(32))
                            ).into(binding.ivSongCover)
                        binding.root.setOnClickListener {
                            MyExoPlayer.startPlaying(binding.root.context, song)
                            it.context.startActivity(Intent(it.context, PlayerActivity::class.java))
                        }

                    }
                }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SectionSongListRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songIdList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(songIdList[position])


    }
}

