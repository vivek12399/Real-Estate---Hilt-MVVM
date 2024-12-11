package com.example.property.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.adapter.TrendingPropertyAdapter
import com.example.property.bottomnavigations.BuilderContactBottomSheetFragment
import com.example.property.databinding.ActivityBuilderDetailBinding
import com.example.property.model.TrendingPropertyModel
import com.example.property.network.models.AuthModels.builder.DataX
import com.example.property.network.models.AuthModels.builder.Property
import com.example.property.network.models.AuthModels.builder.TokenRequest
import com.example.property.network.models.AuthModels.builder.request.TokenReq2
import com.example.property.utils.Constants
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuilderDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityBuilderDetailBinding
    private val viewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    private var propertyComplete:ArrayList<Property>?=null
    private var propertyRunning:ArrayList<Property>?=null
    private var propertyUpcomning:ArrayList<Property>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this)
            .asGif()
            .load(R.drawable.backgif)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.back)

        binding.back.setOnClickListener {
            onBackPressed()
        }
        val userId = intent.getStringExtra("user_id")

        
        var token = tokenManager.getToken()
        if (token!=null){
            viewModel.getBuilderProfile(TokenReq2(token,userId!!))
        }else{
            Toast.makeText(this,"Invalid Authentication Please Try Again", Toast.LENGTH_SHORT).show()
        }
        observableProfile()
        propertyComplete= ArrayList()
        propertyRunning= ArrayList()
        propertyUpcomning= ArrayList()
        setupComplete()
        setupRunning()
        setupUpComing()

        val params = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
            put("property_state", 1)
            if (userId != null) {
                put("user_id",userId)

            }
        }
        viewModel.getCompleteProperty(params)
        observeCompleteResponse()
        val paramsRunning = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
            put("property_state", 2)
            if (userId != null) {
                put("user_id",userId)
            }
        }
        viewModel.getRunningProperty(paramsRunning)
        observeRunningResponse()

        val paramsUp = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
            put("property_state", 3)
            if (userId != null) {
                put("user_id",userId)
            }
        }
        viewModel.getUpcomingProperty(paramsUp)
        observeUpComingResponse()


        binding.contactUs.setOnClickListener {
            val bottomSheetFragment = BuilderContactBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
    private fun observeUpComingResponse() {
        try {
            viewModel.propertyUpcomingResponseLiveData.observe(this) { response ->
                binding.loader.visibility = View.GONE
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            propertyUpcomning!!.clear()
                            if (response.data.data.isNotEmpty()){
                                propertyUpcomning!!.addAll(response.data.data)
                                setupUpComing()
                            }else{

                            }
                        } else {
                            Toast.makeText(this, response.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(this, "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                        binding.loader.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun observeRunningResponse() {
        try {
            viewModel.propertyRunningResponseLiveData.observe(this) { response ->
                binding.loader.visibility = View.GONE
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            propertyRunning!!.clear()
                            if (response.data.data.isNotEmpty()){
                                propertyRunning!!.addAll(response.data.data)
                                setupRunning()
                            }else{

                            }
                        } else {
                            Toast.makeText(this, response.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(this, "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                        binding.loader.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun observeCompleteResponse() {
        try {
            viewModel.propertyCompleteResponseLiveData.observe(this) { response ->
                binding.loader.visibility = View.GONE
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            propertyComplete!!.clear()
                            if (response.data.data.isNotEmpty()){
                                propertyComplete!!.addAll(response.data.data)
                                setupComplete()
                            }else{

                            }
                        } else {
                            Toast.makeText(this, response.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(this, "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                        binding.loader.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun setupComplete(){
        val trendingAdapter = TrendingPropertyAdapter(propertyComplete!!,1,this)
        binding.trendingPropertyRView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.trendingPropertyRView.adapter = trendingAdapter
    }
    private fun setupRunning(){
        val runningProjects = TrendingPropertyAdapter(propertyRunning!!,1,this)
        binding.runningProjectRView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.runningProjectRView.adapter = runningProjects

    }
    private fun setupUpComing(){
        val upcomingAdapter = TrendingPropertyAdapter(propertyUpcomning!!,1,this)
        binding.upcomingProjectRView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.upcomingProjectRView.adapter = upcomingAdapter
    }
    private fun updateUIWithProfileData(data: DataX) {
        binding?.profileImg?.let {
            Glide.with(this).load(Constants.BASE_URL + data.company_pic).into(it)
        }
        binding?.profileLogo?.let {
            Glide.with(this).load(Constants.BASE_URL + data.logo).into(it)
        }

        binding.apply {
            companyName.text = data.company_name
            city.text = data.city_of_office
            objectiveCompany.text = data.company_objective
            ownerName.text = data.owner_name
            cpCount.text = data.completed_projects
            rpCount.text = data.running_projects
            upCount.text = data.upcoming_projects
            since.text = "SINCE "+data.company_since
            exe.text = "EXPERIENCE "+data.company_experience+" YEARS"
            arch.text = data.company_achievement
        }
    }
    private fun observableProfile() {
        viewModel.getProfileResponseLiveData.observe(this, Observer { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val data = response.data?.data
                    if (data != null && response.data.status == 1) {
                        updateUIWithProfileData(data)
                    } else {
                        Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        })
    }
}