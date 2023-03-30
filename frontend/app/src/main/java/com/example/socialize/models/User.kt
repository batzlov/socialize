package com.example.socialize.models

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val isAdmin: Boolean
)
