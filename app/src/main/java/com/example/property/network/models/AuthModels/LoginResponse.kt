package com.example.property.network.models.AuthModels

data class LoginResponse(
    val message: String,
    val status: Int,
    var data : Data
)