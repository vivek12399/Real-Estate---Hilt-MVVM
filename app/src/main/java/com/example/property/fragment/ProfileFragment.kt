package com.example.property.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.property.ui.InquiryAnswerActivity
import com.example.property.ui.MyInquiryActivity
import com.example.property.R
import com.example.property.bottomnavigations.ContactUsBottomSheetFragment
import com.example.property.bottomnavigations.EditProfileBottomSheetFragment
import com.example.property.bottomnavigations.PolicyBottomSheetFragment
import com.example.property.databinding.FragmentProfileBinding
import com.example.property.loginRegister.MainActivity
import com.example.property.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        Glide.with(this)
            .asGif()
            .load(R.drawable.ap_icon)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.appIcon)

        binding.cvMyInquiry.setOnClickListener {
            startActivity(Intent(requireContext(), MyInquiryActivity::class.java))
            requireActivity().overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )

        }
        binding.cvAnswer.setOnClickListener {
            startActivity(Intent(requireContext(), InquiryAnswerActivity::class.java))
            requireActivity().overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )

        }
        binding.cvEditProfile.setOnClickListener {
            EditProfileBottomSheetFragment().show(parentFragmentManager, "EDIT PROFILE")

        }

        binding.cvPolicy.setOnClickListener {
            PolicyBottomSheetFragment().show(parentFragmentManager, "PRIVACY POLICY")

        }

        binding.cvContactUs.setOnClickListener {
            ContactUsBottomSheetFragment().show(parentFragmentManager, "CONTACT US")

        }
        binding.cvLogout.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))
            requireActivity().overridePendingTransition(
                R.anim.catalyst_fade_in,
                R.anim.catalyst_fade_out
            )
            tokenManager.clearToken()
        }
        return binding.root
    }
}