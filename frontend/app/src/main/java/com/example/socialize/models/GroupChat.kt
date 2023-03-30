package com.example.socialize.models

data class GroupChat(
    val id: Int,
    val name: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadMessageCount: Int,
    val image: String
)
