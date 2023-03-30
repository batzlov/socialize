package com.example.socialize.views.authenticated

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialize.models.AuthTokenData
import com.example.socialize.models.AuthUser
import com.example.socialize.util.LocalStorage
import com.example.socialize.viewModels.SignInViewModel
import com.example.socialize.views.authenticated.partials.TopBar
import com.example.socialize.viewModels.SettingsViewModel
import com.example.socialize.views.authenticated.partials.IconButton

@Composable
fun Settings(navController: NavController) {
    val ls: LocalStorage = LocalStorage()

    val (showAllChats, setShowAllChats) = remember { mutableStateOf(false) }
    val (chatDistance, setChatDistance) = remember { mutableStateOf(100f)}

    val (isLoading, setIsLoading) = remember { mutableStateOf(false) }
    var (userSettings, setUserSettings) = remember {
        mutableStateOf(
            com.example.socialize.models.Settings(
                showAllChats = false,
                chatDistance = 100,
                belongsToUser = 0
            )
        )
    }

    val vm: SettingsViewModel = viewModel()
    LaunchedEffect(isLoading) {
        vm.getUserSettingsAsync(
            setUserSettings,
            callback = {
                setShowAllChats(it.showAllChats)
                setChatDistance(it.chatDistance.toFloat())
            }
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.primary)
    ) {
        TopBar(
            title = "Einstellungen"
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White, RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(
                        vertical = 24.dp,
                        horizontal = 18.dp
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Text(
                            "Möchtest du nur Chats in deiner Nähe angezeigt bekommen?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            "Du kannst die Entfernung der angezeigten Chats verändern.",
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = showAllChats,
                        onCheckedChange = {
                            setShowAllChats(!showAllChats)
                        }
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        "alle Chats anzeigen",
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row() {
                    Checkbox(
                        checked = !showAllChats,
                        onCheckedChange = {
                            setShowAllChats(!showAllChats)
                        }
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        "nur Chats in meiner Nähe anzeigen",
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row() {
                    Column() {
                        Text(
                            "Wie weit dürfen die Chats von dir entfernt sein?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            "Gib doch auch Menschen die weiter weg leben eine Chance dich kennen zu lernen.",
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(chatDistance.toInt().toString()+"km")
                        }
                        Slider(
                            value = chatDistance,
                            onValueChange = {
                                setChatDistance(it)
                            },
                            valueRange = 1f..200f
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            "Einstellungen speichern",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            "Nur noch ein tap!",
                            fontSize = 14.sp
                        )
                    }
                    IconButton(
                        icon = Icons.Outlined.Send,
                        "Speichern",
                        callback = {
                            vm.updateUserSettingsAsync(
                                com.example.socialize.models.Settings(
                                    showAllChats = showAllChats,
                                    chatDistance = chatDistance.toInt(),
                                    belongsToUser = 0
                                ),
                                callback = {
                                    setShowAllChats(it.showAllChats)
                                    setChatDistance(it.chatDistance.toFloat())
                                    setIsLoading(false)
                                }
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            "Möchtest du offline gehen?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            "Wir hoffen das du bald zurück bist!",
                            fontSize = 14.sp
                        )
                    }
                    IconButton(
                        icon = Icons.Outlined.ExitToApp,
                        "Abmelden",
                        callback = {
                            vm.signOut()
                            navController.navigate("sign_in")
                        }
                    )
                }
            }
        }
    }
}