package com.example.property.network.models.AuthModels

data class LoginRequest (
    var mobile_no:String,
    var password:String,
    var role_id:String
)