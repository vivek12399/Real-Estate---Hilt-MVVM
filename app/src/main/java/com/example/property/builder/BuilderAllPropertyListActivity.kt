package com.example.property.builder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.adapter.ProperyAdapter
import com.example.property.databinding.ActivityBuilderAllPropertyListBinding
import com.example.property.network.models.AuthModels.builder.Property
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuilderAllPropertyListActivity : AppCompatActivity() {

    private val viewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager

    private lateinit var binding:ActivityBuilderAllPropertyListBinding
    private var properties:ArrayList<Property>?=null
    lateinit var properyAdapter:ProperyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderAllPropertyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        properties= ArrayList()
        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)
        setUpRecyclerView()

        var token = tokenManager.getToken()
        val params = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
            put("filter", "true")
            put("type", 0)
            put("user_id", 0)
        }

        viewModel.getProperty(params)
        observeResponse()


    }

    private fun observeResponse() {
        try {
            viewModel.propertyResponseLiveData.observe(this) { response ->
                binding.loader.visibility = View.GONE
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            properties!!.clear()
                            if (response.data.data.isNotEmpty()){
                                properties!!.addAll(response.data.data)
                                setUpRecyclerView()
                            }else{
                                Toast.makeText(this,"No Property Found",Toast.LENGTH_LONG).show()
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

    private fun setUpRecyclerView(){
        properyAdapter = ProperyAdapter(properties!!,1,this)
        binding.propertyListRView.layoutManager = LinearLayoutManager(this)
        binding.propertyListRView.adapter = properyAdapter
    }

}