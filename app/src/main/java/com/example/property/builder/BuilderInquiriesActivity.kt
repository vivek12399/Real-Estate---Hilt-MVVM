package com.example.property.builder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.R
import com.example.property.adapter.InquiryListAdapter
import com.example.property.builder.adapter.BuilderInquiryAdapter
import com.example.property.databinding.ActivityBuilderInquiriesBinding
import com.example.property.model.InquiryModel

class BuilderInquiriesActivity : AppCompatActivity() {
    lateinit var binding: ActivityBuilderInquiriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderInquiriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)
        val builderInquiryData = ArrayList<InquiryModel>()
        val itemsToAdd = listOf(
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),
            InquiryModel(1, "Home", "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard", "01-12-2022", "asgjasgvjg@gmail..com","Manoj Radadiya"),

            )
        builderInquiryData.addAll(itemsToAdd)
        val builderInquiryAdapter = BuilderInquiryAdapter(builderInquiryData)
        binding.builderInquiryListRView.layoutManager = LinearLayoutManager(this)
        binding.builderInquiryListRView.adapter = builderInquiryAdapter

        if (builderInquiryAdapter.itemCount == 0){
            binding.noRecord.visibility= View.VISIBLE
        }else
        {
            binding.noRecord.visibility= View.GONE
        }
    }
}