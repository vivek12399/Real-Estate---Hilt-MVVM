package com.example.property.builder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.property.builder.builderfragments.BuilderHomeFragment
import com.example.property.builder.builderfragments.BuilderProfileFragment
import com.example.property.databinding.ActivityBuilderMainBinding
import com.example.property.R
import com.example.property.fragment.HomeFragment
import com.example.property.fragment.ProfileFragment
import com.example.property.fragment.WishListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuilderMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuilderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilderMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickEvent(1)

        binding.bottomHome.setOnClickListener {
            clickEvent(1)
        }
        binding.bottomNavProfile.setOnClickListener {
            clickEvent(3)
        }
    }

    private fun clickEvent(id: Int) {
        when (id) {
            1 -> {
                binding.bottomHome.setImageResource(R.drawable.baseline_home_24)
                binding.bottomNavProfile.setImageResource(R.drawable.profile_non)
                exchangeFragment(BuilderHomeFragment())
                overridePendingTransition(
                    R.anim.catalyst_fade_in,
                    R.anim.catalyst_fade_out
                )
            }
            3 -> {
                binding.bottomHome.setImageResource(R.drawable.home_non)
                binding.bottomNavProfile.setImageResource(R.drawable.baseline_person_24)
                exchangeFragment(BuilderProfileFragment())
                overridePendingTransition(
                    R.anim.catalyst_fade_in,
                    R.anim.catalyst_fade_out
                )
            }
        }
    }

    private fun exchangeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.catalyst_fade_in, R.anim.catalyst_fade_out)
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val currentFragment: Fragment? = fragmentManager.findFragmentById(R.id.fragmentContainer)

        // Check if there are any fragments in the back stack
        if (currentFragment is BuilderHomeFragment) {
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
                finishAffinity() // Close all activities
                System.exit(0) // Exit the application
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog
            }
            .show()
    }

}
