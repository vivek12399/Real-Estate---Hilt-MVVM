package com.example.property.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.property.databinding.CatLabelListBinding
import com.example.property.databinding.HomeCategoryItemlistBinding
import com.example.property.model.CategoryItem


class CatagoryLabelAdapter(private val items: MutableList<CategoryItem>) :
    RecyclerView.Adapter<CatagoryLabelAdapter.CategotryLabelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategotryLabelViewHolder {
        val binding = CatLabelListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategotryLabelViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategotryLabelViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CategotryLabelViewHolder(private val binding: CatLabelListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItem) {
            binding.catTitle.text = item.title
        }
    }
    fun addDataAtBeginning(newData: CategoryItem) {
        items.add(0, newData) // Insert the new data at index 0
        notifyItemInserted(0) // Notify the adapter that data has been added at position 0
    }

}
