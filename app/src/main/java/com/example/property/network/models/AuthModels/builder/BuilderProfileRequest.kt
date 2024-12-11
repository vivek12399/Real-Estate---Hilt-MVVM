package com.example.property.network.models.AuthModels.builder

import android.graphics.Bitmap

data class BuilderProfileRequest(
    var userName:String,
    val builderCompanyName: String,
    val email: String,
    val mobileNumber: String,
    val password: String,
    val ownerName: String,
    val companyObjective: String,
    val companyCity: String,
    val companyAchievement: String,
    val companySince: Int,
    val companyExperience: Int,
    val logoBitmap: Bitmap?,
    val profileBitmap: Bitmap?
)
