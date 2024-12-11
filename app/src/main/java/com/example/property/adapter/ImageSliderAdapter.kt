package com.example.property.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.property.databinding.ImageSliderListBinding
import com.example.property.databinding.PopupSliderLayoutBinding
import com.example.property.model.ImageItem
import com.example.property.utils.Constants

class ImageSliderAdapter(private val imageList: List<ImageItem>,private var context: Context) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(val binding: ImageSliderListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val binding = ImageSliderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val imageView = holder.binding.sliderImageView
        Glide.with(context).load(Constants.BASE_URL + imageList[position].imageResId).into(imageView)

    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
