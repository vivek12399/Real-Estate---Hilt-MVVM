package com.example.property.builder

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.builder.builderloginregister.BuilderLoginActivity
import com.example.property.databinding.ActivityBuilderPropertyAddBinding
import com.example.property.network.models.AuthModels.builder.BuilderProfileRequest
import com.example.property.network.models.AuthModels.builder.TokenRequest
import com.example.property.network.models.AuthModels.builder.Type
import com.example.property.network.models.AuthModels.builder.request.PropertyRequest
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class BuilderPropertyAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuilderPropertyAddBinding
    private val viewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    private var selectedPropertyTypeId: String? = null
    private var propertyStatus: Int = -1
    private val IMAGE1_REQ = 1
    private val IMAGE2_REQ = 2
    private val IMAGE3_REQ = 3
    private val IMAGE4_REQ = 4
    private val IMAGE5_REQ = 5
    private var REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 100
    private var REQUEST_PERMISSION_READ_MEDIA_IMAGES = 101
    private var img1: Bitmap? = null
    private var img2: Bitmap? = null
    private var img3: Bitmap? = null
    private var img4: Bitmap? = null
    private var img5: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderPropertyAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)

        var token = tokenManager.getToken()
        if (token!=null){
            viewModel.getPropertyType(TokenRequest(tokenManager.getToken().toString()))
            observableType()
        }else{
            Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
        }

        binding.image1.setOnClickListener { openImageChooser(IMAGE1_REQ) }
        binding.image2.setOnClickListener { openImageChooser(IMAGE2_REQ) }
        binding.image3.setOnClickListener { openImageChooser(IMAGE3_REQ) }
        binding.image4.setOnClickListener { openImageChooser(IMAGE4_REQ) }
        binding.image5.setOnClickListener { openImageChooser(IMAGE5_REQ) }

        binding.status.setOnCheckedChangeListener { _, checkedId ->
            propertyStatus = when (checkedId) {
                R.id.radioButtonC -> 1
                R.id.radioButtonO -> 2
                R.id.radioButtonR -> 3
                else -> -1
            }
        }

        binding.addProperty.setOnClickListener {
            if (validateInputs()) {
                var builderProfileRequest = createBuilderProfileRequest()
                viewModel.addProperty(builderProfileRequest)
                observeResponse()
            }
        }
    }
    private fun checkAndRequestPermissions() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                // Android 13 and above (READ_MEDIA_IMAGES)
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_MEDIA_IMAGES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                        REQUEST_PERMISSION_READ_MEDIA_IMAGES
                    )
                } else {
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                // For Android versions before Android 13 (READ_EXTERNAL_STORAGE)
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
                    )
                } else {

                }
            }
            else -> {

            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            try {
                when (requestCode) {
                    IMAGE1_REQ -> {
                        img1 = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        binding.image1.setImageBitmap(img1)
                    }
                    IMAGE2_REQ -> {
                        img2 = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        binding.image2.setImageBitmap(img2)
                    }
                    IMAGE3_REQ -> {
                        img3 = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        binding.image3.setImageBitmap(img3)
                    }
                    IMAGE4_REQ -> {
                        img4 = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        binding.image4.setImageBitmap(img4)
                    }
                    IMAGE5_REQ -> {
                        img5 = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        binding.image5.setImageBitmap(img5)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun validateInputs(): Boolean {
        val propertyName = binding.userName.text.toString().trim()
        val propertyPrice = binding.price.text.toString().trim()
        val builtYear = binding.years.text.toString().trim()
        val propertyDescription = binding.description.text.toString().trim()
        val propertyFacilities = binding.facilities.text.toString().trim()
        val image1 = img1
        val image2 = img2
        val image3 = img3
        val image4 = img4
        val image5 = img5

        if (propertyName.isEmpty()) {
            binding.userName.error = "Property name is required"
            return false
        }
        if (selectedPropertyTypeId == null) {
            Toast.makeText(this, "Please select a property type", Toast.LENGTH_SHORT).show()
            return false
        }
        if (propertyPrice.isEmpty()) {
            binding.price.error = "Property price is required"
            return false
        }
        if (builtYear.isEmpty()) {
            binding.years.error = "Built year is required"
            return false
        }
        if (propertyStatus == -1) {
            Toast.makeText(this, "Please select a property status", Toast.LENGTH_SHORT).show()
            return false
        }
        if (propertyDescription.isEmpty()) {
            binding.description.error = "Property description is required"
            return false
        }
        if (propertyFacilities.isEmpty()) {
            binding.facilities.error = "Property facilities are required"
            return false
        }
        if (image1 == null && image2 == null && image3 == null && image4 == null && image5 == null) {
            Toast.makeText(this, "At least one image is required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun openImageChooser(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }
    private fun observeResponse() {
        try {
            viewModel.propertyAddResponseLiveData.observe(this) { response ->
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
    private fun observableType() {
        viewModel.getTypeResponseLiveData.observe(this, Observer { response ->
            binding.loader.visibility = View.GONE
            when (response) {
                is NetworkResult.Success -> {
                    val data = response.data?.data
                    if (data != null && response.data.status == 1) {
                        setupPropertyTypeSpinner(data)
                    } else {
                        Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupPropertyTypeSpinner(data: List<Type>) {
        val propertyTypeNames = data.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, propertyTypeNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.propertyTypeSpinner.adapter = adapter

        binding.propertyTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedPropertyTypeId = data[position].id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedPropertyTypeId = null
            }
        }
    }
    private fun createBuilderProfileRequest(): PropertyRequest {
        return PropertyRequest(
            token = tokenManager.getToken().toString(),
            propertyName = binding.userName.text.toString().trim(),
            propertyType = selectedPropertyTypeId!!,
            price = binding.price.text.toString().trim(),
            buildYear = binding.years.text.toString().trim(),
            propertyStatus = propertyStatus.toString(),
            description = binding.description.text.toString().trim(),
            facalities = binding.facilities.text.toString().trim(),
            image1 = img1,
            image2 = img2,
            image3 = img3,
            image4 = img4,
            image5 = img5
        )
    }
}