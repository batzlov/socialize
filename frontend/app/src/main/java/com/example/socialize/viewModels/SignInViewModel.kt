package com.example.socialize.viewModels

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.example.socialize.config.BASE_URL
import com.example.socialize.models.AuthTokenData
import com.example.socialize.models.AuthUser
import com.example.socialize.services.AuthenticationService
import com.example.socialize.util.LocalStorage
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*


class SignInViewModel: ViewModel() {
    private var authService: AuthenticationService

    var ls: LocalStorage = LocalStorage()

    private lateinit var signInRequest: Call<AuthTokenData>
    var signInResponse = mutableStateOf<AuthTokenData?>(null)

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL.trim())
            .build()

        authService = retrofit.create(AuthenticationService::class.java)
    }

    fun signInSync(authUser: AuthUser): AuthTokenData? {
        var authTokenData: AuthTokenData? = null

        try {
            signInRequest = authService.signIn(authUser)
            // calling the .execute() functions block the main thread but runs synchronously
            signInResponse.value = signInRequest.execute().body()
            // overwrite authTokenData with the response body
            authTokenData = signInResponse.value
        } catch(exception: Exception) {
            exception.printStackTrace()
        }

        return authTokenData;
    }

    fun getUserPostalCode(): String {
        return "99085";
    }
}