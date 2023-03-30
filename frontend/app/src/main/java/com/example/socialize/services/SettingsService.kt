package com.example.socialize.services

import com.example.socialize.models.AuthTokenData
import com.example.socialize.models.AuthUser
import com.example.socialize.models.Settings
import com.example.socialize.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface SettingsService {
    @GET("settings/")
    fun getUserSettings(): Call<Settings>

    @PUT("settings/")
    fun updateUserSettings(@Body settings: Settings): Call<Settings>
}