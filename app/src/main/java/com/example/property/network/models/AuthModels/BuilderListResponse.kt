package com.example.property.network.models.AuthModels

data class BuilderListResponse(
    val data: ArrayList<Builder>,
    val message: String,
    val status: Int
)