package com.example.property.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.property.databinding.HomeCategoryItemlistBinding
import com.example.property.model.CategoryItem


class CategoryAdapter(private val items: MutableList<CategoryItem>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = HomeCategoryItemlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CategoryViewHolder(private val binding: HomeCategoryItemlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryItem) {
            binding.homeCategoryImage.setImageResource(item.imageResId)
            binding.homeCategoryTitle.text = item.title
        }
    }
}
