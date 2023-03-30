package com.example.socialize.views.authenticated

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialize.util.LocalStorage
import com.example.socialize.viewModels.ChatViewModel
import com.example.socialize.views.authenticated.partials.IconButton
import com.example.socialize.views.authenticated.partials.TopBar

@Composable
fun Home(navController: NavController) {
    val ls: LocalStorage = LocalStorage()
    val vm: ChatViewModel = viewModel()

    val (messagesCount, setMessagesCount) = remember { mutableStateOf(22) }
    vm.getGroupChatMessagesCountAsync(
        callback = {
            setMessagesCount(it)
        }
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.primary)
    ) {
        TopBar(
            title = "Hallo liebe/r Nutzer:in!",
            starIconDescription = "menu",
            endIconDescription = "search button"
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White, RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        buildAnnotatedString {
                            append("Hallo und Herzlich Willkommen zu ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.W900)
                            ) {
                                append("Socialize!")
                            }
                            append("Mit Hilfe dieser App kannst du dein Leben sozialier gestalten und neue Menschen in deiner Nähe kennenlernen.")
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 15.dp
                    ),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        icon = Icons.Outlined.LocationOn,
                        "Dein Standort"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column() {
                        Text(
                            "Dein Standort",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if(ls.getValue("location") == "-1") {
                            Text("Dein Standort konnte nicht ermittelt werden.")
                        } else {
                            Text(ls.getValue("location"))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        icon = Icons.Outlined.Notifications,
                        "Nachrichten"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column() {
                        Text(
                            "Nachrichten",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Du hast $messagesCount verpasste Nachrichten in Gruppen-Chats in deiner Nähe.")
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 15.dp
                    ),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        icon = Icons.Outlined.Info,
                        "Über Uns"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column() {
                        Text(
                            "Über Uns",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Wenn du mehr über uns und unsere Mission erfahren möchtest, dann besuche unsere Website.")
                    }
                }
            }
        }
    }
}