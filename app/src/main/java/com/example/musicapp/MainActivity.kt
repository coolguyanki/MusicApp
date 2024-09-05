package com.example.musicapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicapp.adapter.CategoryAdapter
import com.example.musicapp.adapter.SectionSongListAdapter
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.models.CategoryModel
import com.example.musicapp.models.SongModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategories()
        setUpSection(
            "section_1",
            binding.section1MainLayout,
            binding.trendingTitle,
            binding.trendingRvCategory
        )

        setUpSection(
            "section_2",
            binding.section2MainLayout,
            binding.section2Title,
            binding.section2RvCategory
        )

        setUpSection(
            "section_3",
            binding.section3MainLayout,
            binding.section3Title,
            binding.section3RvCategory
        )

        setUpMostlyPlayedSection(
            "mostly_played",
            binding.mostlyPlayedMainLayout,
            binding.mostlyPlayedTitle,
            binding.mostlyPlayedRvCategory
        )

        binding.optionsBtn.setOnClickListener {
            showLogOutPopUP()
        }

    }

    fun showLogOutPopUP() {
        val popupMenu = PopupMenu(this, binding.optionsBtn)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.options_menu, popupMenu.menu)
         popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.logOut -> {
                    logOut()
                    true
                }
            }
            false

        }

    }

    fun logOut() {
        MyExoPlayer.getInstance()?.release()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        showPlayerView()
    }

    @SuppressLint("SetTextI18n")
    fun showPlayerView() {
        binding.playerView.setOnClickListener {
            startActivity(Intent(this, PlayerActivity::class.java))
        }
        MyExoPlayer.getCurrentSong()?.let {
            binding.playerView.visibility = View.VISIBLE
            binding.songCoverText.text = "Now Playing : ${it.title}"

            Glide.with(binding.songCoverImageView).load(it.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                ).into(binding.songCoverImageView)

        } ?: run {
            binding.playerView.visibility = View.GONE
        }
    }

    // categories
    private fun getCategories() {
        FirebaseFirestore.getInstance().collection("Category")
            .get().addOnSuccessListener {
                val categoryList = it.toObjects(CategoryModel::class.java)
                setUpCategoryRecyclerView(categoryList)
            }

    }

    fun setUpCategoryRecyclerView(categoryList: List<CategoryModel>) {
        categoryAdapter = CategoryAdapter(categoryList)
        binding.rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.adapter = categoryAdapter
    }

    //sections
    private fun setUpSection(
        id: String,
        mainLayout: RelativeLayout,
        title: TextView,
        recyclerView: RecyclerView
    ) {
        FirebaseFirestore.getInstance().collection("sections")
            .document(id)
            .get().addOnSuccessListener {
                val section = it.toObject(CategoryModel::class.java)
                section?.apply {
                    mainLayout.visibility = View.VISIBLE
                    title.text = name
                    recyclerView.layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    recyclerView.adapter = SectionSongListAdapter(songs)
                    mainLayout.setOnClickListener {
                        SongsListActivity.category = section
                        startActivity(Intent(this@MainActivity, SongsListActivity::class.java))

                    }
                }
            }
    }

    private fun setUpMostlyPlayedSection(
        id: String,
        mainLayout: RelativeLayout,
        title: TextView,
        recyclerView: RecyclerView
    ) {
        FirebaseFirestore.getInstance().collection("sections")
            .document(id)
            .get().addOnSuccessListener {
                //get most played songs
                FirebaseFirestore.getInstance().collection("songs")
                    .orderBy("count", Query.Direction.DESCENDING)
                    .limit(5)
                    .get().addOnSuccessListener { songsList ->
                        val songsModalList = songsList.toObjects<SongModel>()
                        val songsIdList = songsModalList.map {
                            it.id
                        }.toList()
                        val section = it.toObject(CategoryModel::class.java)
                        section?.apply {
                            section.songs = songsIdList
                            mainLayout.visibility = View.VISIBLE
                            title.text = name
                            recyclerView.layoutManager = LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            recyclerView.adapter = SectionSongListAdapter(songs)
                            mainLayout.setOnClickListener {
                                SongsListActivity.category = section
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        SongsListActivity::class.java
                                    )
                                )
                            }
                        }
                    }

            }
    }


}