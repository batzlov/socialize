package com.example.socialize.views.authenticated

import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialize.models.AuthTokenData
import com.example.socialize.models.AuthUser
import com.example.socialize.viewModels.ChatViewModel
import com.example.socialize.views.authenticated.partials.TopBar

@Composable
fun Meet(navController: NavController) {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
    val context = LocalContext.current
    val vm: ChatViewModel = viewModel()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.primary)
    ) {
        TopBar(
            title = "Meet <3",
            hasSearchbar = false
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White, RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Love",
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "WHEN IT'S TIME FOR SOULS TO MEET, THERE'S NOTHING ON EARTH THAT CAN PREVENT THEM FROM MEETING.",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "-- UNKNOWN",
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Es wird jetzt auch für dich Zeit neue Menschen kennen zu lernen. Drück auf den Button und chatte los!")
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .height(40.dp),
                            onClick = {
                                vm.createSocializeChatAsync (
                                    callback = {
                                        Log.d("navigate", "navigate")
                                        navController.navigate("chat/${it.chatId}/${it.chatName}/true")
                                    },
                                    onError = {
                                        Toast.makeText(
                                            context,
                                            "Etwas ist schief gegangen!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        ) {
                            Text(text = "CHATTEN", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}