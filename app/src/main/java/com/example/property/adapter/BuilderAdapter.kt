package com.example.property.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.property.databinding.BuilderListitemBinding
import com.example.property.databinding.HomeCategoryItemlistBinding
import com.example.property.databinding.TopBuilderListitemBinding
import com.example.property.model.CategoryItem
import com.example.property.model.TopBuilderModel

class BuilderAdapter(private var data: List<TopBuilderModel>) :
    RecyclerView.Adapter<BuilderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuilderAdapter.ViewHolder {
        val binding = BuilderListitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuilderAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class ViewHolder(private val binding: BuilderListitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TopBuilderModel) {
            binding.builderListIcon.setImageResource(item.iconResource)
            binding.topBuilderTitle.text = item.title

        }
    }
}
