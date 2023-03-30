package com.example.socialize.viewModels

import android.os.StrictMode
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.socialize.config.BASE_URL
import com.example.socialize.models.AuthUser
import com.example.socialize.models.User
import com.example.socialize.services.AuthenticationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpViewModel: ViewModel() {
    private var authService: AuthenticationService

    private lateinit var signUpRequest: Call<User>
    var signUpResponse = mutableStateOf<User?>(null)

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        authService = retrofit.create(AuthenticationService::class.java)
    }

    fun signUpSync(user: User): User? {
        var createdUser: User? = null

        try {
            signUpRequest = authService.signUp(user)
            signUpResponse.value = signUpRequest.execute().body()
            Log.d("TEST", signUpResponse.value.toString())
            createdUser = signUpResponse.value
        } catch(exception: Exception) {
            exception.printStackTrace()
        }

        return createdUser
    }
}