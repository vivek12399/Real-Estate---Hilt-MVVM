package com.example.property.bottomnavigations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.property.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PolicyBottomSheetFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_policy_bottom_sheet, container, false)
    }


}