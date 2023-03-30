package com.example.socialize.views.authenticated.partials.chat

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.socialize.models.Message
import com.example.socialize.viewModels.ChatViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatSection(vm: ChatViewModel, modifier: Modifier = Modifier, messages: List<Message>, listState: LazyListState) {
    // scroll down to the end of the list
    LaunchedEffect(key1 = Unit, block = {
        listState.scrollToItem(messages.size)
    })

    if(messages.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text("In diesem Chat gibt es noch keine Nachrichten. Trau dich und schreibe die erste Nachricht!")
        }
    } else {
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(vm.messages) { chat ->
                MessageItem(
                    chat.text,
                    chat.author!!,
                    chat.sentAt!!,
                    chat.userIsAuthor
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}