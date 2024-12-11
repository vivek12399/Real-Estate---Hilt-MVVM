package com.example.property.network.models.AuthModels

data class UserRegisterRequest(
    val email: String,
    val mobile_no: String,
    val password: String,
    val question_answer: String,
    val question_id: String,
    val role_id: String,
    val username: String
)