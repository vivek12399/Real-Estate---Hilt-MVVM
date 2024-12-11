package com.example.property.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.property.ui.BuilderDetailActivity
import com.example.property.ui.BuilderListActivity
import com.example.property.R
import com.example.property.databinding.TopBuilderListitemBinding
import com.example.property.model.TopBuilderModel
import com.example.property.network.models.AuthModels.Builder
import com.example.property.utils.Constants

class TopBuilderAdapter(private var data: ArrayList<Builder>,private var context: Context) :
    RecyclerView.Adapter<TopBuilderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopBuilderAdapter.ViewHolder {
        val binding = TopBuilderListitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopBuilderAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class ViewHolder(private val binding: TopBuilderListitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Builder) {
            if (item.company_pic.isNullOrEmpty()) {
                binding.builderListIcon.setImageResource(R.drawable.appicon)
            } else {
                Glide.with(context).load(Constants.BASE_URL + item.company_pic)
                    .into(binding.builderListIcon)
            }
            binding.topBuilderTitle.text = item.company_name


            // Set a click listener on the item view
            binding.root.setOnClickListener {
                val intent = Intent(context, BuilderDetailActivity::class.java).putExtra(
                    "user_id",
                    item.user_id
                )
                // You can add extra data to the intent if needed
                // intent.putExtra("key", "value")
                context.startActivity(intent)
            }
        }

    }
}
