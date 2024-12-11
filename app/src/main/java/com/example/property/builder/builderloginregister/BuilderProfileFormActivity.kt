package com.example.property.builder.builderloginregister

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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.builder.BuilderMainActivity
import com.example.property.databinding.ActivityBuilderProfileFormBinding
import com.example.property.network.models.AuthModels.builder.BuilderProfileRequest

import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.userAgent
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class BuilderProfileFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuilderProfileFormBinding
    private val viewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    private val PICK_LOGO_REQUEST = 1
    private val PICK_PROFILE_REQUEST = 2
    private var logoBitmap: Bitmap? = null
    private var profileBitmap: Bitmap? = null
    private var REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 100
    private var REQUEST_PERMISSION_READ_MEDIA_IMAGES = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderProfileFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileLogo.setOnClickListener { openImageChooser(PICK_LOGO_REQUEST) }
        binding.profilePicture.setOnClickListener { openImageChooser(PICK_PROFILE_REQUEST) }

        binding.btnReg.setOnClickListener {
            if (validateInputs()) {

                try {
                    binding.includeProgressBar.progressBarLayout.visibility = View.VISIBLE
                    var builderProfileRequest = createBuilderProfileRequest()
                    viewModel.builderProfile(builderProfileRequest)
                    binding.includeProgressBar.progressBarLayout.visibility = View.GONE

                }catch (e: Exception){
                    e.printStackTrace()
                    Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }

            }
        }
        checkAndRequestPermissions()
        observeResponse()
    }

    private fun observeResponse() {
        try {
            viewModel.profileResponseLiveData.observe(this) { response ->
                binding.includeProgressBar.progressBarLayout.visibility = View.GONE
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data?.status?.equals(1) == true) {
                            Toast.makeText(this, response.data.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, BuilderLoginActivity::class.java))
                            overridePendingTransition(
                                R.anim.catalyst_fade_in,
                                R.anim.catalyst_fade_out
                            )
                            finish()
                        } else {
                            Toast.makeText(this, response.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(this, "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        // Handle loading state if needed
                        binding.includeProgressBar.progressBarLayout.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
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



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun openImageChooser(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            try {
                when (requestCode) {
                    PICK_LOGO_REQUEST -> {
                        logoBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        binding.profileLogo.setImageBitmap(logoBitmap)
                    }
                    PICK_PROFILE_REQUEST -> {
                        profileBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        binding.profilePicture.setImageBitmap(profileBitmap)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        with(binding) {
            if (userName?.text.isNullOrEmpty()) {
                userName.error = "Builder Company Name is required"
                isValid = false
            } else {
                userName.error = null
            }
            if (companyName?.text.isNullOrEmpty()) {
                companyName.error = "Builder Company Name is required"
                isValid = false
            } else {
                companyName.error = null
            }

            if (edtEmail?.text.isNullOrEmpty()) {
                edtEmail.error = "Email is required"
                isValid = false
            } else {
                edtEmail.error = null
            }

            if (edtMobile?.text.isNullOrEmpty()) {
                edtMobile.error = "Mobile number is required"
                isValid = false
            } else {
                edtMobile.error = null
            }

            if (edtPass?.text.isNullOrEmpty()) {
                edtPass.error = "Password is required"
                isValid = false
            } else {
                edtPass.error = null
            }

            if (ownerName?.text.isNullOrEmpty()) {
                ownerName.error = "Owner name is required"
                isValid = false
            } else {
                ownerName.error = null
            }

            if (logoBitmap == null) {
                Toast.makeText(this@BuilderProfileFormActivity, "Company logo is required", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (profileBitmap == null) {
                Toast.makeText(this@BuilderProfileFormActivity, "Profile picture is required", Toast.LENGTH_SHORT).show()
                isValid = false
            }
        }

        return isValid
    }

    private fun createBuilderProfileRequest(): BuilderProfileRequest {
        return BuilderProfileRequest(
            userName= binding.userName.text.toString(),
            builderCompanyName = binding.companyName.text.toString(),
            email = binding.edtEmail.text.toString(),
            mobileNumber = binding.edtMobile.text.toString(),
            password = binding.edtPass.text.toString(),
            ownerName = binding.ownerName.text.toString(),
            companyObjective = binding.companyObjective.text.toString(),
            companyCity = binding.companyCity.text.toString(),
            companyAchievement = binding.companyAchievement.text.toString(),
            companySince = binding.companySince.text.toString().toInt(),
            companyExperience = binding.companyExperience.text.toString().toInt(),
            logoBitmap = logoBitmap,
            profileBitmap = profileBitmap
        )
    }
}