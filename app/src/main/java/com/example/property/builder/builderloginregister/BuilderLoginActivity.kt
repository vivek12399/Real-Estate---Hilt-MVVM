package com.example.property.builder.builderloginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.builder.BuilderMainActivity
import com.example.property.databinding.ActivityBuilderLoginBinding
import com.example.property.loginRegister.MainActivity
import com.example.property.network.models.AuthModels.LoginRequest
import com.example.property.ui.DashBoardActivity
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class BuilderLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityBuilderLoginBinding
    private val authViewModel by viewModels<MyViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newAccount.setOnClickListener {
            startActivity(Intent(this,BuilderProfileFormActivity::class.java))
            overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }
        binding.user.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }
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
                authViewModel.userLogin(LoginRequest(binding.edtMno.text.toString(), binding.edtPass.text.toString(),"2"))
            }

        }
        builderLogin()
    }
    private fun builderLogin() {
        authViewModel.loginResponseLiveData.observe(this,
            Observer { response ->
                binding.includeProgressBar.progressBarLayout.visibility = View.GONE
                when(response){
                    is NetworkResult.Success -> {
                        //token
                        if (response.data?.status?.equals(1) == true){
                            tokenManager.saveToken(response.data.data.token)
                            tokenManager.saveUserId(response.data.data.id)
                            startActivity(Intent(this,BuilderMainActivity::class.java))
                            overridePendingTransition(
                                R.anim.catalyst_fade_in,
                                R.anim.catalyst_fade_out
                            )
                            finish()
                        }else{
                            Toast.makeText(this,response.data!!.message,Toast.LENGTH_SHORT).show()
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