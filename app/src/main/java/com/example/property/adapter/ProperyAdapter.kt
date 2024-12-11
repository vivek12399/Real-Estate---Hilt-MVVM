package com.example.property.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.property.R
import com.example.property.databinding.PropertyListitemBinding
import com.example.property.network.models.AuthModels.builder.Property
import com.example.property.ui.PropertyDetailActivity
import com.example.property.utils.Constants

class ProperyAdapter(
    private var data: ArrayList<Property>,
    private var isFromBuilder: Int,
    private var context: Context
) : RecyclerView.Adapter<ProperyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProperyAdapter.ViewHolder {
        val binding = PropertyListitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProperyAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: PropertyListitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Property) {
            if (item.photos.isNullOrEmpty()) {
                binding.propertyListImage.setImageResource(R.drawable.appicon)
            } else {
                val photo = item.photos[0]
                Glide.with(context).load(Constants.BASE_URL + photo).into(binding.propertyListImage)
            }
            binding.PropertyListTitle.text = item.name
            binding.PropertyListDetails.text = "Details :  ${item.description}"
            binding.PropertyPrice.text = "Price : ${item.price}"
            binding.PropertyLocation.text = "Built In : ${item.built_in}"
        }

        init {
            binding.root.setOnClickListener {
                val context = it.context
                val property = data[adapterPosition]
                val intent = Intent(context, PropertyDetailActivity::class.java).apply {
                    putExtra("isFromBuilder", isFromBuilder)
                    putExtra("name", property.name)
                    putExtra("property_id", property.id)
                    putExtra("description", property.description)
                    putExtra("price", property.price)
                    putExtra("built_in", property.built_in)
                    putExtra("faca", property.facility)
                    putStringArrayListExtra("photos", ArrayList(property.photos))
                }
                context.startActivity(intent)
            }
        }
    }
}
