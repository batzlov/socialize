package com.example.socialize.models

data class AuthUser(
    val email: String,
    val password: String,
    val latitude: Float,
    val longitude: Float
)
