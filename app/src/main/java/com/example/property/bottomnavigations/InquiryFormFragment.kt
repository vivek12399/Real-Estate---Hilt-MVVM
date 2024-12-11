package com.example.property.bottomnavigations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.property.R
import com.example.property.databinding.FragmentProfileBinding
import com.example.property.databinding.InquiryFormDesginBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InquiryFormFragment : BottomSheetDialogFragment() {
    private var _binding: InquiryFormDesginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InquiryFormDesginBinding.inflate(inflater, container, false)

        return binding.root

    }

}
