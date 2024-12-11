package com.example.property.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.R
import com.example.property.adapter.InquiryAnswerListAdapter
import com.example.property.databinding.ActivityInquiryAnswerBinding
import com.example.property.model.AnswerModel

class InquiryAnswerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityInquiryAnswerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquiryAnswerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)
        val answerData = ArrayList<AnswerModel>()
        val itemsToAdd = listOf(
            AnswerModel(1, "Home","01-11-2022", "03-11-2022",  "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard","Literally it does not mean anything. It is a sequence of words without a sense of Latin derivation that make up a text also known as filler text, fictitious, blind or placeholder"),
            AnswerModel(1, "Home","01-11-2022", "03-11-2022",  "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard","Literally it does not mean anything. It is a sequence of words without a sense of Latin derivation that make up a text also known as filler text, fictitious, blind or placeholder"),
            AnswerModel(1, "Home","01-11-2022", "03-11-2022",  "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard","Literally it does not mean anything. It is a sequence of words without a sense of Latin derivation that make up a text also known as filler text, fictitious, blind or placeholder"),
            AnswerModel(1, "Home","01-11-2022", "03-11-2022",  "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard","Literally it does not mean anything. It is a sequence of words without a sense of Latin derivation that make up a text also known as filler text, fictitious, blind or placeholder"),
            AnswerModel(1, "Home","01-11-2022", "03-11-2022",  "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard","Literally it does not mean anything. It is a sequence of words without a sense of Latin derivation that make up a text also known as filler text, fictitious, blind or placeholder"),
            AnswerModel(1, "Home","01-11-2022", "03-11-2022",  "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard","Literally it does not mean anything. It is a sequence of words without a sense of Latin derivation that make up a text also known as filler text, fictitious, blind or placeholder"),
            AnswerModel(1, "Home","01-11-2022", "03-11-2022",  "orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard","Literally it does not mean anything. It is a sequence of words without a sense of Latin derivation that make up a text also known as filler text, fictitious, blind or placeholder"),

            )
        answerData.addAll(itemsToAdd)
        val answerAdapter = InquiryAnswerListAdapter(answerData)
        binding.answerRView.layoutManager = LinearLayoutManager(this)
        binding.answerRView.adapter = answerAdapter
        if (answerAdapter.itemCount == 0){
            binding.noRecord.visibility= View.VISIBLE
        }else
        {
            binding.noRecord.visibility= View.GONE
        }





    }
}