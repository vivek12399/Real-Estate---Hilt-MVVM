package com.example.property.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.property.databinding.InquiryListitemBinding
import com.example.property.model.InquiryModel

class InquiryListAdapter(private var itemList:ArrayList<InquiryModel>) :RecyclerView.Adapter<InquiryListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InquiryListAdapter.ViewHolder {
        val binding = InquiryListitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InquiryListAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(private val binding:InquiryListitemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: InquiryModel) {
            binding.inquiryId.text = "${item.id}"
            binding.date.text = "${item.date}"
            binding.userName.text = "${item.userName}"
            binding.userEmail.text = "${item.email}"
            binding.title.text = "For : ${item.title}"
            binding.iDesc.text = "Inquiry Message : ${item.desc}"
        }
    }
}