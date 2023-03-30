package com.example.socialize.views.authenticated

import android.os.StrictMode
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.socialize.models.ChatEntry
import com.example.socialize.models.GroupChat
import com.example.socialize.models.Location
import com.example.socialize.util.LocalStorage
import com.example.socialize.viewModels.ChatViewModel
import com.example.socialize.views.authenticated.partials.ChatItem
import com.example.socialize.views.authenticated.partials.TopBar

@Composable
fun Locations(navController: NavController) {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)

    val ls: LocalStorage = LocalStorage()

    val location: Location = Location(
        -1f,
        -1f
    )
    if(ls.getValue("location") != "-1") {
        location.latitude = ls.getValue("latitude").toFloat()
        location.longitude = ls.getValue("longitude").toFloat()
    }

    val vm: ChatViewModel = viewModel()
    vm.getGroupChatsAsync(location)

    val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.primary)
    ) {
        TopBar(
            title = "Locations",
            endIcon = Icons.Outlined.Search,
            starIconDescription = "menu",
            endIconDescription = "search button",
            hasSearchbar = true,
            searchQuery = searchQuery,
            setSearchQuery = {
                setSearchQuery(it)
            }
        )
        if(vm.getGroupChatsResponse != null) {
            var groupChats: Array<GroupChat> = arrayOf<GroupChat>()
            // check if the searchQuery is set, if so, filter the group chats
            if(searchQuery.isNotEmpty()) {
                vm.getGroupChatsResponse!!.forEach {
                    if(it.name.contains(searchQuery, ignoreCase = true)) {
                        groupChats = groupChats.plus(it)
                    }
                }
            } else {
                groupChats = vm.getGroupChatsResponse!!;
            }

            if(groupChats.isNotEmpty()) {
                LazyColumn(modifier = Modifier
                    .background(Color.White, RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp))
                    .padding(
                        bottom = 52.dp
                    )
                    .weight(1f)) {
                    items(groupChats) { chat ->
                        Spacer(modifier = Modifier.height(8.dp))
                        ChatItem(
                            chat.id,
                            chat.name,
                            chat.lastMessage,
                            chat.unreadMessageCount,
                            chat.image,
                            chat.lastMessageTime,
                            navController
                        )
                    }
                }
            } else {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White, RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Unter diesem Namen existieren keine Chats..")
                }
            }
        } else {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White, RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Wir suchen nach Chats in deiner Nähe.")
                Spacer(modifier = Modifier.height(8.dp))
                CircularProgressIndicator()
            }
        }
    }
}