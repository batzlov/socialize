package com.example.socialize.models

data class ChatEntry(
    val userName: String,
    val lastMessage: String,
    val unreadMessageCont: Int,
    val profile: String,
    val lastMessageTime: String
)