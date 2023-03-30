package com.example.socialize.models

data class Message(
    val id: Int = 0,
    val text: String,
    val author: String?,
    val sentAt: String?,
    val fromId: Int,
    val toId: Int = 0,
    val isGroupChatMessage: Boolean = true,
    val toGroupId: Int,
    val userIsAuthor: Boolean = true
)
