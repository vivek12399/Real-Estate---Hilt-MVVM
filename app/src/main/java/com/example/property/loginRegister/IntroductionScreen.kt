package com.example.property.loginRegister

import IntroductionPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.property.R
import com.example.property.databinding.ActivityIntroductionScreenBinding

class IntroductionScreen : AppCompatActivity() {
    private lateinit var binding: ActivityIntroductionScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val introductionPagerAdapter = IntroductionPagerAdapter(this, this,binding.viewPager)
        binding.viewPager.adapter = introductionPagerAdapter

        binding.nextButton.setOnClickListener {
            introductionPagerAdapter.moveToNextPage()
        }


    }
}