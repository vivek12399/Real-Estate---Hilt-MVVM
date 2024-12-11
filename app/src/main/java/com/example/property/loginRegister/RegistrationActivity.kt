package com.example.property.loginRegister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.property.MyViewModel
import com.example.property.R
import com.example.property.databinding.ActivityRegistrationBinding
import com.example.property.network.models.AuthModels.UserRegisterRequest
import com.example.property.ui.DashBoardActivity
import com.example.property.utils.NetworkResult
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding

    @Inject
    lateinit var tokenManager: TokenManager
    private val viewModel by viewModels<MyViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newAccount.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }

        binding.btnReg.setOnClickListener {
            if (binding.userName.text.toString().isEmpty() || binding.edtPass.text.toString().isEmpty() || binding.edtEmail.text.toString().isEmpty()
                || binding.edtMobile.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                viewModel.userRegister(UserRegisterRequest(binding.edtEmail.text.toString(), binding.edtMobile.text.toString(),binding.edtPass.text.toString(),"m bjb","3","1",binding.userName.text.toString()))
            }

        }
        bindObserve()

    }

    private fun bindObserve(){
        viewModel.registerResponseLiveData.observe(this) { response ->
            binding.includeProgressBar.progressBarLayout.visibility = View.GONE
            when (response) {
                is NetworkResult.Success -> {
                    if (response.data?.status?.equals(1) == true){
                        tokenManager.saveToken(response.data.data.token)
                        tokenManager.saveRole(response.data.data.role_id)
                        tokenManager.saveUserId(response.data.data.id)
                        startActivity(Intent(this, DashBoardActivity::class.java))
                        overridePendingTransition(
                            R.anim.catalyst_fade_in,
                            R.anim.catalyst_fade_out
                        )
                        finish()
                    }else{
                        Toast.makeText(this, response.data?.message, Toast.LENGTH_LONG).show()
                    }

                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, "Something Went Wrong..!! Please Contact Admin", Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    binding.includeProgressBar.progressBarLayout.visibility = View.VISIBLE
                }
            }
        }
    }
}