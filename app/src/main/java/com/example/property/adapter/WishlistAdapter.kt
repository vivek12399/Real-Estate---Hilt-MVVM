package com.example.property.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.property.ui.PropertyDetailActivity
import com.example.property.R
import com.example.property.databinding.WishlistDesignBinding
import com.example.property.model.PropertyModel


class WishlistAdapter(private var data: ArrayList<PropertyModel>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishlistAdapter.ViewHolder {
        val binding = WishlistDesignBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class ViewHolder(private val binding: WishlistDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PropertyModel) {
            binding.propertyListImage.setImageResource(item.iconResource)
            binding.PropertyListTitle.text = "${item.title}"
            binding.PropertyListDetails.text = "Details :  ${item.desc}"
            binding.PropertyPrice.text = "Price : ${item.price}"
            binding.PropertyLocation.text = "Location : ${item.location}"


        }
        init {
            binding.root.setOnClickListener {
                val context = it.context
                // Clicked on the item, handle the click for a different activity
                val intent = Intent(context, PropertyDetailActivity::class.java)
                (context as AppCompatActivity).overridePendingTransition(
                    R.anim.catalyst_fade_in,
                    R.anim.catalyst_fade_out
                )
                // You can add extra data to the intent if needed
                // intent.putExtra("key", "value")
                context.startActivity(intent)
            }
        }
    }
}