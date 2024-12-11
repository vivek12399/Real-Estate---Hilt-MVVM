package com.example.property.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.R
import com.example.property.databinding.ActivityDashboardBinding
import com.example.property.fragment.HomeFragment
import com.example.property.fragment.ProfileFragment
import com.example.property.fragment.WishListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardActivity: AppCompatActivity() {


    private lateinit var binding:ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        Glide.with(this)
            .asGif() // Specify that you're loading a GIF
            .load(R.drawable.ap_icon) // Replace with the actual resource ID of your GIF
            .diskCacheStrategy(DiskCacheStrategy.DATA) // Caches the original data of the GIF
            .transition(DrawableTransitionOptions.withCrossFade()) // Cross-fade animation
            .into(binding.includeProgressBar.appIconLoader)

        setContentView(view)
        binding.includeProgressBar.progressBarLayout.visibility = View.VISIBLE
        clickEvent(1)


        binding.bottomHome.setOnClickListener {
            binding.includeProgressBar.progressBarLayout.visibility = View.VISIBLE
            clickEvent(1)

        }
        binding.bottomWish.setOnClickListener {
            binding.includeProgressBar.progressBarLayout.visibility = View.VISIBLE
            clickEvent(2)

        }
        binding.bottomNavProfile.setOnClickListener {
            binding.includeProgressBar.progressBarLayout.visibility = View.VISIBLE
            clickEvent(3)

        }
    }

    private fun clickEvent(id:Int){
        when(id){
            1 ->{
                binding.bottomHome.setImageResource(R.drawable.baseline_home_24)
                binding.bottomWish.setImageResource(R.drawable.favorite_non)
                binding.bottomNavProfile.setImageResource(R.drawable.profile_non)

                exchangeFragment(HomeFragment())

            }
            2 ->{
                binding.bottomHome.setImageResource(R.drawable.home_non)
                binding.bottomWish.setImageResource(R.drawable.baseline_favorite_24)
                binding.bottomNavProfile.setImageResource(R.drawable.profile_non)
                exchangeFragment(WishListFragment())
            }
            3 ->{
                binding.bottomHome.setImageResource(R.drawable.home_non)
                binding.bottomWish.setImageResource(R.drawable.favorite_non)
                binding.bottomNavProfile.setImageResource(R.drawable.baseline_person_24)
                exchangeFragment(ProfileFragment())
            }
        }
    }

    private fun exchangeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.catalyst_fade_in, R.anim.catalyst_fade_out)
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
            binding.includeProgressBar.progressBarLayout.visibility = View.GONE
        fragmentTransaction.commit()
    }
    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val currentFragment: Fragment? = fragmentManager.findFragmentById(R.id.fragmentContainer)

        // Check if there are any fragments in the back stack
        if (currentFragment is HomeFragment) {
            // If you are on the initial fragment, show the exit confirmation dialog
            showExitConfirmationDialog()
        } else {
            fragmentManager.popBackStack()
        }
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit App")
            .setMessage("Are you sure you want to exit the app?")
            .setPositiveButton("Yes") { _, _ ->
                finishAffinity()
                System.exit(0)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog
            }
            .show()
    }
}
