package com.example.property.builder.builderfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.property.MyViewModel
import com.example.property.adapter.TrendingPropertyAdapter
import com.example.property.databinding.FragmentBuilderHomeBinding
import com.example.property.network.models.AuthModels.builder.Property
import com.example.property.network.models.AuthModels.builder.TokenRequest
import com.example.property.network.models.AuthModels.builder.request.TokenReq2
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BuilderHomeFragment : Fragment() {

    private var _binding :FragmentBuilderHomeBinding?=null
    private val viewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    private val binding
    get()=_binding!!

    private var propertyComplete:ArrayList<Property>?=null
    private var propertyRunning:ArrayList<Property>?=null
    private var propertyUpcomning:ArrayList<Property>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuilderHomeBinding.inflate(layoutInflater)

        propertyComplete= ArrayList()
        propertyRunning= ArrayList()
        propertyUpcomning= ArrayList()
        setupComplete()
        setupRunning()
        setupUpComing()
        var token = tokenManager.getToken()
        var user_id=tokenManager.getUSerId()
        val params = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
            put("property_state", 1)
            if (user_id != null) {
                put("user_id",user_id)
            }
        }
        viewModel.getCompleteProperty(params)
        observeCompleteResponse()
        val paramsRunning = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
            put("property_state", 2)
            if (user_id != null) {
                put("user_id",user_id)
            }
        }
        viewModel.getRunningProperty(paramsRunning)
        observeRunningResponse()

        val paramsUp = HashMap<String, Any>().apply {
            if (token != null) {
                put("token", token)
            }
            put("property_state", 3)
            if (user_id != null) {
                put("user_id",user_id)
            }
        }
        viewModel.getUpcomingProperty(paramsUp)
        observeUpComingResponse()

        if (token!=null){
            viewModel.getBuilderProfile(TokenReq2(token,""))
        }else{
            Toast.makeText(requireContext(),"Invalid Authentication Please Try Again",Toast.LENGTH_SHORT).show()
        }
        observableProfile()
        return binding.root

    }
    private fun observeUpComingResponse() {
        try {
            viewModel.propertyUpcomingResponseLiveData.observe(requireActivity()) { response ->
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
    private fun observeRunningResponse() {
        try {
            viewModel.propertyRunningResponseLiveData.observe(requireActivity()) { response ->
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
    private fun observeCompleteResponse() {
        try {
            viewModel.propertyCompleteResponseLiveData.observe(requireActivity()) { response ->
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
    private fun setupComplete(){
        val trendingAdapter = TrendingPropertyAdapter(propertyComplete!!,1,requireContext())
        binding.trendingPropertyRView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trendingPropertyRView.adapter = trendingAdapter
    }
    private fun setupRunning(){
        val runningProjects = TrendingPropertyAdapter(propertyRunning!!,1,requireContext())
        binding.runningProjectRView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.runningProjectRView.adapter = runningProjects

    }
    private fun setupUpComing(){
        val upcomingAdapter = TrendingPropertyAdapter(propertyUpcomning!!,1,requireContext())
        binding.upcomingProjectRView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.upcomingProjectRView.adapter = upcomingAdapter
    }
    private fun observableProfile() {
        viewModel.getProfileResponseLiveData.observe(requireActivity(),
            Observer { response ->
                binding.loader.visibility = View.GONE
                when(response){
                    is NetworkResult.Success -> {
                        var data = response.data!!.data

                        if (response.data.status==1){
                            binding.apply {
                                companyName.text=data.company_name
                                ownerName.text = data.owner_name
                                cpCount.text = data.completed_projects
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(),response.message, Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResult.Loading -> {
                        binding.loader.visibility = View.VISIBLE
                    }
                }
            })
    }
}