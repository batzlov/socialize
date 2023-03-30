package com.example.socialize.services

import com.example.socialize.models.*
import retrofit2.Call
import retrofit2.http.*

interface GroupChatsService {
    @GET("group-chats/socialize")
    fun createSocializeChat(): Call<SocializeChatData>

    @DELETE("group-chats/socialize")
    fun deleteSocializeChat(): Call<Any?>

    @POST("group-chats/")
    fun getGroupChats(@Body location: Location): Call<Array<GroupChat>>

    @GET("group-chats/{id}")
    fun getGroupChatById(@Path("id") id: Int): Call<GroupChat>

    @GET("group-chats/{id}/messages")
    fun getGroupChatMessages(@Path("id") id: Int): Call<Array<Message>>
}