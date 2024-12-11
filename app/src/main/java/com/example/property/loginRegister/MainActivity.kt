package com.example.property.loginRegister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.MyViewModel
import com.example.property.ui.DashBoardActivity
import com.example.property.R
import com.example.property.builder.builderloginregister.BuilderLoginActivity
import com.example.property.databinding.ActivityMainBinding
import com.example.property.network.models.AuthModels.LoginRequest
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    private lateinit var binding:ActivityMainBinding

    private val authViewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        tokenManager.isFirstTimeInDashboard(false)
        Glide.with(this)
            .asGif() // Specify that you're loading a GIF
            .load(R.drawable.ap_icon) // Replace with the actual resource ID of your GIF
            .diskCacheStrategy(DiskCacheStrategy.DATA) // Caches the original data of the GIF
            .transition(DrawableTransitionOptions.withCrossFade()) // Cross-fade animation
            .into(binding.includeProgressBar.appIconLoader)
        binding.btnLogin.setOnClickListener {
            if (binding.edtMno.text.toString().isEmpty() || binding.edtPass.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                /*startActivity(Intent(this, DashBoardActivity::class.java))
                overridePendingTransition(
                    R.anim.catalyst_fade_in,
                    R.anim.catalyst_fade_out
                )*/
                authViewModel.userLogin(LoginRequest(binding.edtMno.text.toString(), binding.edtPass.text.toString(),"1"))
            }

        }
        binding.newAccount.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }
        binding.builderLogin.setOnClickListener {
            startActivity(Intent(this, BuilderLoginActivity::class.java))
            overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }

        userLogin()
    }

    private fun userLogin() {
        authViewModel.loginResponseLiveData.observe(this,
            Observer { response ->
                binding.includeProgressBar.progressBarLayout.visibility = View.GONE
                when(response){
                    is NetworkResult.Success -> {
                        //token
                        if (response.data!!.status == 1){
                            tokenManager.saveToken(response.data!!.data.token)
                            tokenManager.saveRole(response.data.data.role_id)
                            Toast.makeText(this,response.data.message,Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, DashBoardActivity::class.java))
                            overridePendingTransition(
                                R.anim.catalyst_fade_in,
                                R.anim.catalyst_fade_out
                            )
                            finish()
                        }else{
                            Toast.makeText(this,response.data.message,Toast.LENGTH_SHORT).show()
                        }

                    }
                    is NetworkResult.Error -> {
                        Toast.makeText(this, "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        binding.includeProgressBar.progressBarLayout.visibility = View.VISIBLE
                    }
                }
            })
    }


}