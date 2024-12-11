package com.example.property.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.property.databinding.InquiryAnsweryItemlistBinding
import com.example.property.databinding.InquiryListitemBinding
import com.example.property.model.AnswerModel
import com.example.property.model.InquiryModel


class InquiryAnswerListAdapter(private var itemList:ArrayList<AnswerModel>) : RecyclerView.Adapter<InquiryAnswerListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InquiryAnswerListAdapter.ViewHolder {
        val binding = InquiryAnsweryItemlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InquiryAnswerListAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(private val binding: InquiryAnsweryItemlistBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: AnswerModel) {
            binding.inquiryId.text = "${item.id}"
            binding.inquiryDate.text = "At :${item.i_date}"
            binding.ansDate.text = item.a_date
            binding.inquiryAnswer.text = item.inquiry_ans
            binding.title.text = "For : ${item.title}"
            binding.inquiryMsg.text = item.inquiry_msg
        }
    }
}