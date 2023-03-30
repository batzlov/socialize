package com.example.socialize.views.authenticated

import android.annotation.SuppressLint
import android.os.Handler
import android.os.StrictMode
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.socialize.R
import com.example.socialize.models.Message
import com.example.socialize.util.SocketHandler
import com.example.socialize.viewModels.ChatViewModel
import com.example.socialize.views.authenticated.partials.chat.ChatHeaderSection
import com.example.socialize.views.authenticated.partials.chat.ChatSection
import com.example.socialize.views.authenticated.partials.chat.MessageItem
import com.example.socialize.views.authenticated.partials.chat.MessageSection
import com.google.gson.Gson
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun Chat(navController: NavController, chatId: Int, chatName: String, isPrivateChat: Boolean) {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)

    val vm: ChatViewModel = viewModel()
    vm.getGroupChatMessages(chatId)

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // initialize socket connection
    SocketHandler.setSocket()
    SocketHandler.establishConnection()

    val socket = SocketHandler.getSocket()
    socket.on("message") { args ->
        if(args[0] != null) {
            vm.getGroupChatMessages(chatId)

            // handles the scrolling to the last message in the list
            coroutineScope.launch {
                listState.animateScrollToItem(vm.messages.size)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ChatHeaderSection(
            username = chatName,
            profile = rememberAsyncImagePainter("https://images.unsplash.com/photo-1551893478-d726eaf0442c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"),
            isOnline = true,
            onBack = {
                navController.navigateUp()
            },
            isPrivateChat = isPrivateChat
        )
        ChatSection(vm , Modifier.weight(1f), vm.messages, listState)
        MessageSection(vm, socket, chatId, Integer.parseInt(vm.ls.getValue("userId").trim()), vm.messages)
    }
}