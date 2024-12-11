package com.example.property.builder.builderloginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.property.R
import com.example.property.databinding.ActivityBuilderRegistrationBinding

class BuilderRegistrationActivity : AppCompatActivity() {

    lateinit var binding:ActivityBuilderRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReg.setOnClickListener {
        }
        binding.newAccount.setOnClickListener{
            startActivity(Intent(this,BuilderLoginActivity::class.java))
            overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
        }

    }
}