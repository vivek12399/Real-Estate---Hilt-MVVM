package com.example.property.network.models.AuthModels.builder

data class PropertyResponse(
    val message: String,
    val data: ArrayList<Property>,
    val status: Int
)