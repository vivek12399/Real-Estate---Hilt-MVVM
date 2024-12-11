package com.example.property.adapter
import android.content.Context
import android.provider.ContactsContract.Contacts.Photo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.property.databinding.PhotosListBinding
import com.example.property.model.ImageItem
import com.example.property.utils.Constants


class ImageAdapter(
    private val imageList: List<ImageItem>,
    private val onClickListener: (Int) -> Unit,
    private var context: Context
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: PhotosListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = PhotosListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageView = holder.binding.imageView
        /*imageView.setImageResource(imageList[position].imageResId)*/
        Glide.with(context).load(Constants.BASE_URL + imageList[position].imageResId).into(imageView)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
