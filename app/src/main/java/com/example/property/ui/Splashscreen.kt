package com.example.property.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.R
import com.example.property.builder.BuilderMainActivity
import com.example.property.databinding.ActivitySpalshScreenBinding
import com.example.property.loginRegister.IntroductionScreen
import com.example.property.loginRegister.MainActivity
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Splashscreen : AppCompatActivity() {

    private lateinit var binding: ActivitySpalshScreenBinding

    @Inject
    lateinit var tokenManager: TokenManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        Glide.with(this)
            .asGif() // Specify that you're loading a GIF
            .load(R.drawable.ap_icon) // Replace with the actual resource ID of your GIF
            .diskCacheStrategy(DiskCacheStrategy.DATA) // Caches the original data of the GIF
            .transition(DrawableTransitionOptions.withCrossFade()) // Cross-fade animation
            .into(binding.appLogo)


        Handler().postDelayed( {

            if (tokenManager.isFirstTime()){
                startActivity(Intent(this,IntroductionScreen::class.java))
                overridePendingTransition(
                    R.anim.catalyst_fade_in,
                    R.anim.catalyst_fade_out
                )
                finishAffinity()
            }else{
                var token = tokenManager.getToken()
                if (token == null){
                    startActivity(Intent(this,MainActivity::class.java))
                    overridePendingTransition(
                        R.anim.catalyst_fade_in,
                        R.anim.catalyst_fade_out
                    )
                    finishAffinity()
                }else{
                    if (tokenManager.getRole()=="1"){
                        startActivity(Intent(this, DashBoardActivity::class.java))
                        overridePendingTransition(
                            R.anim.catalyst_fade_in,
                            R.anim.catalyst_fade_out
                        )
                        finishAffinity()
                    }else{
                        startActivity(Intent(this, BuilderMainActivity::class.java))
                        overridePendingTransition(
                            R.anim.catalyst_fade_in,
                            R.anim.catalyst_fade_out
                        )
                        finishAffinity()
                    }
                }

            }

        },2000)

    }
}