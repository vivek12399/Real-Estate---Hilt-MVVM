package com.example.property.network.models.AuthModels.builder

data class TypeResponse(
    val message: String,
    val status: Int,
    val data: List<Type>
)