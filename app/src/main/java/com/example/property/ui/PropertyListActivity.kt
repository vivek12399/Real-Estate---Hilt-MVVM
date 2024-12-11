package com.example.property.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.R
import com.example.property.adapter.ProperyAdapter
import com.example.property.databinding.ActivityPropertyListBinding
import com.example.property.model.PropertyModel

class PropertyListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPropertyListBinding
    lateinit var trendingAdapter:ProperyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)

        val trendingData = ArrayList<PropertyModel>()
        val itemsToAdd = listOf(
            PropertyModel(R.drawable.flatpicture, "Flat", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "Rajkot", "₹ 70,00,000"),
            PropertyModel(R.drawable.housepicture, "House", "This is beautiful Home with balcony and parking Area , totally 3 floor available here", "Rajkot", "₹ 1,000,0000"),
            PropertyModel(R.drawable.officepictures, "Office", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "Rajkot", "₹ 70,00,000"),
            PropertyModel(R.drawable.flatpicture, "Flat", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "Rajkot", "₹ 70,00,000"),
            PropertyModel(R.drawable.housepicture, "House", "This is beautiful Home with balcony and parking Area , totally 3 floor available here", "Rajkot", "₹ 1,000,0000"),
            PropertyModel(R.drawable.officepictures, "Office", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "Rajkot", "₹ 70,00,000"),
            PropertyModel(R.drawable.flatpicture, "Flat", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "Rajkot", "₹ 70,00,000"),
            PropertyModel(R.drawable.housepicture, "House", "This is beautiful Home with balcony and parking Area , totally 3 floor available here", "Rajkot", "₹ 1,000,0000"),
            PropertyModel(R.drawable.officepictures, "Office", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "Rajkot", "₹ 70,00,000"),

            )
        trendingData.addAll(itemsToAdd)
        //trendingAdapter = ProperyAdapter(trendingData,0)
        binding.propertyListRView.layoutManager = LinearLayoutManager(this)
        binding.propertyListRView.adapter = trendingAdapter
    }
}