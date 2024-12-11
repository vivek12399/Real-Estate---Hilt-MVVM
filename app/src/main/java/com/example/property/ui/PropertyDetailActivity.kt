package com.example.property.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.adapter.ImageAdapter
import com.example.property.adapter.ImageSliderAdapter
import com.example.property.adapter.SilderAdapter
import com.example.property.bottomnavigations.InquiryFormFragment
import com.example.property.builder.BuilderMainActivity
import com.example.property.databinding.ActivityPropertyDetailBinding
import com.example.property.databinding.PopupSliderLayoutBinding
import com.example.property.model.ImageItem
import com.example.property.utils.Constants
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PropertyDetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    lateinit var binding: ActivityPropertyDetailBinding
    private var isFromBuilder: Int? = null
    private var photos: ArrayList<String>? = null



    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve intent extras
        isFromBuilder = intent.getIntExtra("isFromBuilder", 0)
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")
        val builtIn = intent.getStringExtra("built_in")
        val faca = intent.getStringExtra("faca")
        val property_id = intent.getStringExtra("id")
        photos = intent.getStringArrayListExtra("photos")

        // Set text and visibility
        binding.propertyDetailTitle.text = name
        binding.facalites.text = faca
        binding.propertyDetailDescription.text = description
        binding.propertyDetailPrice.text = "₹ : $price"
        binding.propertyDetailBuiltIn.text = "Built In : $builtIn"

        if (photos.isNullOrEmpty()) {
            binding.sliderView.setImageResource(R.drawable.appicon)
        } else {
            val photo = photos!![0]
            Glide.with(this).load(Constants.BASE_URL + photo).into(binding.sliderView)
        }
        if (isFromBuilder == 1) {
            binding.inquiryButton.visibility = View.GONE
            binding.deleteButton.visibility = View.VISIBLE
        }

        binding.deleteButton.setOnClickListener {
            var token = tokenManager.getToken()
            val params = HashMap<String, Any>().apply {
                if (token != null) {
                    put("token", token)
                }
                if (property_id != null) {
                    put("property_id",property_id)
                }
            }
            viewModel.propertyDelete(params)
            observeResponse()
        }
        binding.inquiryButton.setOnClickListener {
            val inquiryFormFragment = InquiryFormFragment()
            inquiryFormFragment.show(supportFragmentManager, inquiryFormFragment.tag)
            overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }


        // Load background GIF with Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.backgif)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.back)

        // Handle back button click
        binding.back.setOnClickListener {
            onBackPressed()
        }

        // Setup RecyclerView for image thumbnails
        val recyclerView: RecyclerView = binding.imgs
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        // Create list of ImageItems from photos
        val imageList = photos?.map { ImageItem(it) } ?: listOf()

        // Set adapter for RecyclerView
        val adapter = ImageAdapter(imageList, { position ->
            showImageSlider(imageList, position)
        },this)
        recyclerView.adapter = adapter
    }
    private fun observeResponse() {
        try {
            viewModel.propertyDelResponseLiveData.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            Toast.makeText(this, response.data.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, BuilderMainActivity::class.java))
                            overridePendingTransition(
                                R.anim.catalyst_fade_in,
                                R.anim.catalyst_fade_out
                            )
                        } else {
                            Toast.makeText(this, response.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(this, "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    // Function to show image slider dialog
    private fun showImageSlider(imageList: List<ImageItem>, initialPosition: Int) {
        val dialogBinding = PopupSliderLayoutBinding.inflate(layoutInflater)
        val imageSlider = dialogBinding.imageSlider

        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        val window = dialog.window
        val layoutParams = window?.attributes

        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT

        window?.attributes = layoutParams

        val adapter = ImageSliderAdapter(imageList,this)
        imageSlider.adapter = adapter
        imageSlider.setCurrentItem(initialPosition, false)

        dialog.show()

        val closeButton = dialogBinding.closeButton
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}
