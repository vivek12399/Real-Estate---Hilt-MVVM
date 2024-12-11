package com.example.property.bottomnavigations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.property.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditProfileBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile_bottom_sheet, container, false)

        // Display data in the BottomSheet


        return view
    }
}
