package com.example.property.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.R
import com.example.property.adapter.ProperyAdapter
import com.example.property.adapter.WishlistAdapter
import com.example.property.databinding.FragmentHomeBinding
import com.example.property.databinding.FragmentWishListBinding
import com.example.property.model.PropertyModel


class WishListFragment : Fragment() {

    private var _binding: FragmentWishListBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishListBinding.inflate(inflater, container, false)

        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)

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

            )
        trendingData.addAll(itemsToAdd)
        val trendingAdapter = WishlistAdapter(trendingData)
        binding.propertyListRView.layoutManager = LinearLayoutManager(requireContext())
        binding.propertyListRView.adapter = trendingAdapter

        return binding.root
    }

}