package com.example.property.network.models.AuthModels.builder

data class Property(
    val built_in: String,
    val current_state: String,
    val description: String,
    val facility: String,
    val id: String,
    val name: String,
    val photos: List<String>,
    val price: String,
    val type: String,
    val user_id: String
)