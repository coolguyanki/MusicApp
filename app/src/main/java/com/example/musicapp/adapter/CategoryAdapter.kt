package com.example.musicapp.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicapp.SongsListActivity
import com.example.musicapp.databinding.CategoryItemRecyclerRowBinding
import com.example.musicapp.models.CategoryModel

class CategoryAdapter (private val categoryList: List<CategoryModel>):
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CategoryItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(categoryList[position])
    }

class MyViewHolder(private val binding: CategoryItemRecyclerRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
        // bind the data with root
        @RequiresApi(Build.VERSION_CODES.S)
        fun bindData(category: CategoryModel) {
            binding.tvName.text = category.name
            Glide.with(binding.ivCover).load(category.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                ).into(binding.ivCover)

            // start song list activity
            val context = binding.root.context
            binding.root.setOnClickListener {
                SongsListActivity.category = category
                context.startActivity(Intent(context, SongsListActivity::class.java))
            }

        }

    }

}