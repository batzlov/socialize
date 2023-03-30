package com.example.socialize.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialize.config.BASE_URL
import com.example.socialize.models.*
import com.example.socialize.services.GroupChatsService
import com.example.socialize.util.LocalStorage
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ChatViewModel: ViewModel() {
    var messages by mutableStateOf<List<Message>>(listOf())

    private var groupChatsService: GroupChatsService

    var ls: LocalStorage = LocalStorage()
    var authToken: String = ls.getValue("authToken")

    private lateinit var getGroupChatsRequest: Call<Array<GroupChat>>
    var getGroupChatsResponse: Array<GroupChat>? = arrayOf<GroupChat>()
    /* var getGroupChatsResponse = mutableStateOf<Array<GroupChat>?>(null) */

    private lateinit var getGroupChatMessagesRequest: Call<Array<Message>>
    var getGroupChatMessagesResponse: Array<Message>? = arrayOf<Message>()
    var getGroupChatMessagesResponse2 = mutableListOf<Message>()

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${this.authToken.trim()}").build()
                chain.proceed(request)
            }.build())
            .build()

        groupChatsService = retrofit.create(GroupChatsService::class.java)
    }

    fun getGroupChats(location: Location): Array<GroupChat> {
        var groupChats: Array<GroupChat> = arrayOf();

        try {
            getGroupChatsRequest = groupChatsService.getGroupChats(location)
            getGroupChatsResponse = getGroupChatsRequest.execute().body()

            groupChats = getGroupChatsResponse!!
        } catch(exception: Exception) {
            exception.printStackTrace()
        }

        return groupChats
    }

    fun getGroupChatsAsync(location: Location) {
        getGroupChatsRequest = groupChatsService.getGroupChats(location)
        getGroupChatsRequest.enqueue(
            object: Callback<Array<GroupChat>> {
                override fun onResponse(
                    call: Call<Array<GroupChat>>,
                    response: Response<Array<GroupChat>>
                ){
                    Log.d("HTTP-REQUEST", "Started request to get locations")
                    response.body()?.let{p ->
                        getGroupChatsResponse = p;
                    }
                }

                override fun onFailure(call: Call<Array<GroupChat>>, t: Throwable) {
                    Log.d("MESSAGE", t.toString())
                    t.printStackTrace()
                }
            }
        )
    }

    fun getGroupChatMessages(id: Int): Array<Message> {
        var _messages: Array<Message> = arrayOf();

        try {
            getGroupChatMessagesRequest = groupChatsService.getGroupChatMessages(id)
            getGroupChatMessagesResponse = getGroupChatMessagesRequest.execute().body()
            messages = getGroupChatMessagesResponse!!.toList()
            _messages = getGroupChatMessagesResponse!!
        } catch(exception: Exception) {
            exception.printStackTrace()
        }

        return _messages
    }

    fun createSocializeChatAsync(callback: (SocializeChatData) -> Unit, onError: () -> Unit) {
        val createSocializeChat = groupChatsService.createSocializeChat()
        createSocializeChat.enqueue(
            object: Callback<SocializeChatData> {
                override fun onResponse(
                    call: Call<SocializeChatData>,
                    response: Response<SocializeChatData>
                ){
                    if(response.isSuccessful) {
                        response.body()?.let{p ->
                            callback(p)
                        }
                    } else {
                        onError()
                    }
                }
                override fun onFailure(call: Call<SocializeChatData>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }

    fun deleteSocializeChatAsync(callback: () -> Unit, onError: () -> Unit) {
        val deleteSocializeChat = groupChatsService.deleteSocializeChat()
        deleteSocializeChat.enqueue(
            object: Callback<Any?> {
                override fun onResponse(
                    call: Call<Any?>,
                    response: Response<Any?>
                ){
                    if(response.isSuccessful) {
                        response.body()?.let{p ->
                            callback()
                        }
                    } else {
                        onError()
                    }
                }
                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }

    fun getGroupChatMessagesCountAsync(callback: (Int) -> Unit) {
        val getUserSettings = groupChatsService.getGroupChats(Location(-1f, -1f))
        getUserSettings.enqueue(
            object: Callback<Array<GroupChat>> {
                override fun onResponse(
                    call: Call<Array<GroupChat>>,
                    response: Response<Array<GroupChat>>
                ){
                    response.body()?.let{p ->
                        var summedMessages = 0
                        p.forEach {
                            summedMessages += it.unreadMessageCount
                        }
                        callback(summedMessages)
                    }
                }

                override fun onFailure(call: Call<Array<GroupChat>>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }

    fun getGroupChatMessagesAsync(id: Int) {
        getGroupChatMessagesRequest = groupChatsService.getGroupChatMessages(id)
        getGroupChatMessagesRequest.enqueue(
            object: Callback<Array<Message>> {
                override fun onResponse(
                    call: Call<Array<Message>>,
                    response: Response<Array<Message>>
                ){
                    response.body()?.let{p ->
                        getGroupChatMessagesResponse2 = p.toMutableList();
                        messages = p.toMutableList();
                        Log.d("MESSAGE!!!!!!!!!", p.toString())
                    }
                }

                override fun onFailure(call: Call<Array<Message>>, t: Throwable) {
                    Log.d("MESSAGE", "FAILED REQUEST")
                    t.printStackTrace()
                }
            }
        )
    }
}