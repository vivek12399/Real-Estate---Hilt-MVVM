package com.example.property.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.property.adapter.InquiryListAdapter
import com.example.property.databinding.ActivityMyInquiryBinding
import com.example.property.model.InquiryModel

class MyInquiryActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyInquiryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyInquiryBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val inquiryData = ArrayList<InquiryModel>()
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
        inquiryData.addAll(itemsToAdd)
        val inquiryAdapter = InquiryListAdapter(inquiryData)
        binding.inquiryListRView.layoutManager = LinearLayoutManager(this)
        binding.inquiryListRView.adapter = inquiryAdapter





        if (inquiryAdapter.itemCount == 0){
            binding.noRecord.visibility=View.VISIBLE
        }else
        {
            binding.noRecord.visibility=View.GONE
        }



    }
}