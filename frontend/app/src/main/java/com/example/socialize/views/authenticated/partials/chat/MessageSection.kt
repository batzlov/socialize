package com.example.socialize.views.authenticated.partials.chat

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.socialize.R
import com.example.socialize.models.Message
import com.example.socialize.viewModels.ChatViewModel
import com.google.gson.Gson
import io.socket.client.Socket

var message = mutableStateOf("")

@Composable
fun MessageSection(vm: ChatViewModel, socket: Socket, chatId: Int, userId: Int, messages: List<Message>) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {
        OutlinedTextField(
            placeholder = {
                Text("Deine Nachricht..")
            },
            value = message.value,
            onValueChange = {
                message.value = it
            },
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable {
                        if(!message.value.isEmpty()) {
                            val m = Message(
                                id = 0,
                                text = message.value,
                                fromId = userId,
                                author = "",
                                sentAt = "",
                                toId = 0,
                                isGroupChatMessage = true,
                                toGroupId = chatId,
                                userIsAuthor = true
                            )
                            val gson = Gson()
                            val json = gson.toJson(m, Message::class.java)
                            Log.d("HELP", "HELP")
                            socket.emit("message", json)
                            message.value = ""
                        }
                    }
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
        )
    }
}