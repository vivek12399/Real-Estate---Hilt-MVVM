package com.example.property.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.property.*
import com.example.property.databinding.TrendingPropertyItemlistBinding
import com.example.property.model.TrendingPropertyModel
import com.example.property.network.models.AuthModels.builder.Property
import com.example.property.ui.PropertyDetailActivity
import com.example.property.ui.PropertyListActivity
import com.example.property.utils.Constants

class TrendingPropertyAdapter(private var data: ArrayList<Property>,private var isFromBuilder:Int,private var context: Context) :
    RecyclerView.Adapter<TrendingPropertyAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: TrendingPropertyItemlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Property) {

            if (item.photos.isNullOrEmpty()) {
                binding.trendingPropertyIcon.setImageResource(R.drawable.appicon)
            } else {
                val photo = item.photos[0]
                Glide.with(context).load(Constants.BASE_URL + photo).into(binding.trendingPropertyIcon)
            }
            binding.trendingPropertyTitle.text = "${item.name}"
            binding.trendingPropertyShortDetail.text = "Description: ${item.description}"
            binding.trendingPropertyCategory.text = "Category: ${item.type}"
            binding.trendingPropertyPrice.text = "Price: ${item.price}"

        }
        init {
            // Set a click listener on the item view
            binding.root.setOnClickListener {
                val context = it.context

                    // Clicked on the item, handle the click for a different activity
                    val property = data[adapterPosition]
                    val intent = Intent(context, PropertyDetailActivity::class.java).apply {
                        putExtra("isFromBuilder", isFromBuilder)
                        putExtra("property_id", property.id)
                        putExtra("name", property.name)
                        putExtra("description", property.description)
                        putExtra("price", property.price)
                        putExtra("built_in", property.built_in)
                        putExtra("faca", property.facility)
                        putStringArrayListExtra("photos", ArrayList(property.photos))
                    }

                    // You can add extra data to the intent if needed
                    // intent.putExtra("key", "value")
                    context.startActivity(intent)
                }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingPropertyAdapter.ViewHolder {
        val binding = TrendingPropertyItemlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TrendingPropertyAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

}
