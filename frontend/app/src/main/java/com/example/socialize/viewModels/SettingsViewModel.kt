package com.example.socialize.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.socialize.config.BASE_URL
import com.example.socialize.models.GroupChat
import com.example.socialize.models.Message
import com.example.socialize.models.Settings
import com.example.socialize.services.AuthenticationService
import com.example.socialize.services.GroupChatsService
import com.example.socialize.services.SettingsService
import com.example.socialize.util.LocalStorage
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingsViewModel: ViewModel() {
    private var settingsService: SettingsService

    var ls: LocalStorage = LocalStorage()
    var authToken: String = ls.getValue("authToken")

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${this.authToken.trim()}").build()
                chain.proceed(request)
            }.build())
            .build()


        settingsService = retrofit.create(SettingsService::class.java)
    }

    // TODO: handle sign out
    fun signOut() {
        ls.clear()
    }

    fun getUserSettingsAsync(setUserSettings: ((Settings) -> Unit), callback: (settings: Settings) -> Unit) {
        val getUserSettings = settingsService.getUserSettings()
        getUserSettings.enqueue(
            object: Callback<Settings> {
                override fun onResponse(
                    call: Call<Settings>,
                    response: Response<Settings>
                ){
                    response.body()?.let{p ->
                        callback(p)
                    }
                }

                override fun onFailure(call: Call<Settings>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }

    fun updateUserSettingsAsync(settings: Settings, callback: (settings: Settings) -> Unit) {
        val updateUserSettings = settingsService.updateUserSettings(settings)
        updateUserSettings.enqueue(
            object: Callback<Settings> {
                override fun onResponse(
                    call: Call<Settings>,
                    response: Response<Settings>
                ){
                    response.body()?.let{p ->
                        callback(p)
                        Log.d("HELP", p.toString())
                    }
                }

                override fun onFailure(call: Call<Settings>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }
}