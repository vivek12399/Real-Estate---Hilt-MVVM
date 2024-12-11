package com.example.property.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.adapter.*
import com.example.property.databinding.FragmentHomeBinding
import com.example.property.model.CategoryItem
import com.example.property.model.TopBuilderModel
import com.example.property.model.TrendingPropertyModel
import com.example.property.network.models.AuthModels.Builder
import com.example.property.network.models.AuthModels.builder.Property
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var sliderAdapter: SilderAdapter
    private lateinit var recyclerView: RecyclerView
    private var builders:ArrayList<Builder>?=null
    private lateinit var builderAdapter: TopBuilderAdapter
    private lateinit var trendingAdapter: TrendingPropertyAdapter
    private var properties:ArrayList<Property>?=null
    private val images = intArrayOf(
        R.drawable.bgimg, R.drawable.appbgimg, R.drawable.appbgimg,
        R.drawable.appbgimg,
        R.drawable.appbgimg,  R.drawable.appbgimg, R.drawable.appbgimg, R.drawable.appbgimg, R.drawable.appbgimg, R.drawable.appbgimg, R.drawable.appbgimg)
    private var currentPage = 0

    private val handler = Handler()
    private val binding get() = _binding!!
    private val update: Runnable = object : Runnable {
        override fun run() {
            if (currentPage == images.size) {
                currentPage = 0
            }
            binding.sliderView.setCurrentItem(currentPage, true)
            currentPage++
            handler.postDelayed(this, 2000) // Slide every 2 seconds
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        builders= ArrayList()
        properties= ArrayList()
        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)
        Glide.with(this)
            .asGif() // Specify that you're loading a GIF
            .load(R.drawable.ap_icon) // Replace with the actual resource ID of your GIF
            .diskCacheStrategy(DiskCacheStrategy.DATA) // Caches the original data of the GIF
            .transition(DrawableTransitionOptions.withCrossFade()) // Cross-fade animation
            .into(binding.includeProgressBar.appIconLoader)
        Glide.with(this)
            .asGif()
            .load(R.drawable.notification)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.notificationIcon)
        //slider code
        sliderAdapter = SilderAdapter(requireContext(), images)
        binding.sliderView.adapter = sliderAdapter
        handler.post(update) // Start auto-sliding



        //
        val items = mutableListOf(
            CategoryItem("Apartment", R.drawable.appartmenticon),
            CategoryItem("Plot", R.drawable.ploticon),
            CategoryItem("Commercial", R.drawable.comicon),
            CategoryItem("Villah", R.drawable.villahicon),
            CategoryItem("Residential", R.drawable.resicon)
        )
        val adapter = CategoryAdapter(items)
        binding.homeCategoryRView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.homeCategoryRView.adapter = adapter



        //
        val catLblAdapter = CatagoryLabelAdapter(items)
        binding.categotryForTrendingProperty.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categotryForTrendingProperty.adapter = catLblAdapter
        val newDataItem = CategoryItem("All",R.drawable.appicon)
        catLblAdapter.addDataAtBeginning(newDataItem)
        var token = tokenManager.getToken()
        val params2 = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
            put("filter", "false")
            put("type", 0)
            put("user_id", 0)
        }
       viewModel.getProperty(params2)
        observlesForProperty()
       /* //trending
        val trendingData = ArrayList<TrendingPropertyModel>()
        val itemsToAdd = listOf(
            TrendingPropertyModel(R.drawable.flatpicture, "Flat", "Apartment", "This is beautiful 3BHK flat with balcony and buccati sofa added", "₹ 70,00,000"),
            TrendingPropertyModel(R.drawable.housepicture, "House", "Tenament", "This is beautiful Home with balcony and parking Area , totally 3 floor available here", "₹ 1,000,0000"),
            TrendingPropertyModel(R.drawable.officepictures, "Office", "Commercial", "Office in JASAL COMPLEX", "₹ 50,00,000")
        )
        trendingData.addAll(itemsToAdd)
        //trendingAdapter =TrendingPropertyAdapter(trendingData,0,requireContext())
        binding.trendingPropertyRView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trendingPropertyRView.adapter = trendingAdapter
*/
        //builder

        val params = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
        }
        viewModel.getBuilderList(params)
        builderObservles()


        binding.includeProgressBar.progressBarLayout.visibility = View.GONE
        return binding.root
    }

    private fun observlesForProperty() {
        try {
            viewModel.propertyResponseLiveData.observe(requireActivity()) { response ->
                binding.loader.visibility = View.GONE
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            properties!!.clear()
                            if (response.data.data.isNotEmpty()){
                                properties!!.addAll(response.data.data)
                                setUpRView()
                            }else{
                                Toast.makeText(requireContext(),"No Property Found",Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), response.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                        binding.loader.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpRView(){
        trendingAdapter =TrendingPropertyAdapter(properties!!,0,requireContext())
        binding.trendingPropertyRView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trendingPropertyRView.adapter = trendingAdapter
    }
    private fun builderObservles() {
        try {
            viewModel.builderListResponseLiveData.observe(requireActivity()) { response ->
                binding.loader.visibility = View.GONE
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            builders!!.clear()
                            if (response.data.data.isNotEmpty()){
                                builders!!.addAll(response.data.data)
                                builderRView()
                            }else{
                                Toast.makeText(requireContext(),"No Property Found", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), response.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                        binding.loader.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun builderRView(){
        recyclerView = binding.TopTrendingBuilderRView
        builderAdapter = TopBuilderAdapter(builders!!,requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = builderAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacks(update)
    }
}