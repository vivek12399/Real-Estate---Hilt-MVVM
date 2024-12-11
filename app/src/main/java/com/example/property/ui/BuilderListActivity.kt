package com.example.property.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.R
import com.example.property.adapter.BuilderAdapter
import com.example.property.databinding.ActivityBuilderListBinding
import com.example.property.model.TopBuilderModel

class BuilderListActivity : AppCompatActivity() {
    private lateinit var builderAdapter: BuilderAdapter
    private lateinit var binding: ActivityBuilderListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)
        val topBuilder = listOf(
            TopBuilderModel("Ladani Group.", R.drawable.builderimage),
            TopBuilderModel("Meet Builders.", R.drawable.meetbuilder),
            TopBuilderModel("RK Group", R.drawable.rkbuilder)
        )
        builderAdapter = BuilderAdapter(topBuilder)
        binding.TopTrendingBuilderRView.layoutManager= LinearLayoutManager(this)
        binding.TopTrendingBuilderRView.adapter = builderAdapter
    }
}