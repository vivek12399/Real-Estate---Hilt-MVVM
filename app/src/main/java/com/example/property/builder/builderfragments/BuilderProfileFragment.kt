package com.example.property.builder.builderfragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.builder.BuilderAllPropertyListActivity
import com.example.property.builder.BuilderInquiriesActivity
import com.example.property.builder.BuilderMainActivity
import com.example.property.builder.BuilderPropertyAddActivity
import com.example.property.builder.builderloginregister.BuilderLoginActivity
import com.example.property.builder.builderloginregister.BuilderProfileFormActivity
import com.example.property.databinding.FragmentBuilderProfileBinding
import com.example.property.network.models.AuthModels.builder.DataX
import com.example.property.network.models.AuthModels.builder.TokenRequest
import com.example.property.network.models.AuthModels.builder.request.BuilderProfileUpdate
import com.example.property.network.models.AuthModels.builder.request.TokenReq2
import com.example.property.utils.Constants.BASE_URL
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import com.google.firebase.firestore.util.Executors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BuilderProfileFragment : Fragment() {

    private var _binding:FragmentBuilderProfileBinding?=null
    private val viewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    private val binding
    get() = _binding!!
    var companyName=""
    var ownerName=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuilderProfileBinding.inflate(layoutInflater)


        binding.cvAllProperty.setOnClickListener {
            startActivity(Intent(requireContext(),BuilderAllPropertyListActivity::class.java))
            requireActivity().overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }
        binding.cvAddProperty.setOnClickListener {
            startActivity(Intent(requireContext(),BuilderPropertyAddActivity::class.java))
            requireActivity().overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }
        binding.cvMyInquiry.setOnClickListener {
            startActivity(Intent(requireContext(),BuilderInquiriesActivity::class.java))
            requireActivity().overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }
        binding.cvLOGOUT.setOnClickListener {
            tokenManager.clearToken()
            startActivity(Intent(requireContext(),BuilderLoginActivity::class.java))
            requireActivity().overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
            requireActivity().finish()

        }
        binding.edit.setOnClickListener {
            showEditProfileDialog()
            requireActivity().overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }
        var token = tokenManager.getToken()
        if (token!=null){
            viewModel.getBuilderProfile(TokenReq2(token,""))
        }else{
            Toast.makeText(requireContext(),"Invalid Authentication Please Try Again",Toast.LENGTH_SHORT).show()
        }
        observableProfile()
        return binding.root
    }
    private fun updateUIWithProfileData(data: DataX) {
        binding?.profileImg?.let {
            Glide.with(requireActivity()).load(BASE_URL + data.company_pic).into(it)
        }
        binding?.profileLogo?.let {
            Glide.with(requireActivity()).load(BASE_URL + data.logo).into(it)
        }

        binding.apply {
            companyName.text = data.company_name
            city.text = data.city_of_office
            objective.text = data.company_objective
            ownerName.text = data.owner_name
            cpCount.text = data.completed_projects
            rpCount.text = data.running_projects
            upCount.text = data.upcoming_projects
        }
    }
    private fun observableProfile() {
        viewModel.getProfileResponseLiveData.observe(viewLifecycleOwner, Observer { response ->
            binding.loader.visibility = View.GONE

            when (response) {
                is NetworkResult.Success -> {
                    val data = response.data?.data
                    if (data != null && response.data.status == 1) {
                        updateUIWithProfileData(data)
                    } else {
                        Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
            }
        })
    }
    private fun showEditProfileDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_profile, null)
        val editCompanyName = dialogView.findViewById<EditText>(R.id.updateCompanyName)
        val editOwnerName = dialogView.findViewById<EditText>(R.id.updateOwnerName)
        val btnSave = dialogView.findViewById<Button>(R.id.updateBtn)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        editCompanyName.setText(binding.companyName.text)
        editOwnerName.setText(binding.ownerName.text)

        btnSave.setOnClickListener {
            if (editCompanyName.text.isNotEmpty()&&editOwnerName.text.isNotEmpty()) {
                val token = tokenManager.getToken()
                if (token != null) {
                    viewModel.updateBuilderProfile(BuilderProfileUpdate(token, editCompanyName.text.toString(), editOwnerName.text.toString()) )
                    observeResponse()
                } else {
                    Toast.makeText(requireContext(), "Invalid Authentication Please Try Again", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun observeResponse() {
        try {
            viewModel.getProfileUpdateResponse.observe(requireActivity()) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            Toast.makeText(requireContext(), response.data.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(requireContext(), BuilderMainActivity::class.java))
                        } else {
                            Toast.makeText(requireContext(), response.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


}