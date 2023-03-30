package com.example.socialize.services

import com.example.socialize.models.AuthTokenData
import com.example.socialize.models.AuthUser
import com.example.socialize.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationService {
    @POST("sign-in/")
    fun signIn(@Body authUser: AuthUser): Call<AuthTokenData>

    @POST("sign-up/")
    fun signUp(@Body user: User): Call<User>

    @GET("sign-out")
    fun signOut(): Call<Boolean>
}