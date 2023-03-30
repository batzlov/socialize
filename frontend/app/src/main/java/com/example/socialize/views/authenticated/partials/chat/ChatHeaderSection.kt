package com.example.socialize.views.authenticated.partials.chat

import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialize.models.User
import com.example.socialize.viewModels.ChatViewModel

@Composable
fun ChatHeaderSection(
    username: String,
    profile: Painter,
    isOnline: Boolean,
    isPrivateChat: Boolean,
    onBack: () -> Unit
) {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
    val context = LocalContext.current
    val vm: ChatViewModel = viewModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        backgroundColor = Color(0xFFFAFAFA),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(!isPrivateChat) {
                IconButton(
                    onClick = onBack
                ){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Image(
                painter = profile,
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = username, fontWeight = FontWeight.SemiBold)
                Text(
                    text = if (isOnline) "Online" else "Offline",
                    fontSize = 12.sp
                )
            }

            if(isPrivateChat) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                              vm.deleteSocializeChatAsync(
                                  callback = {
                                      onBack()
                                  },
                                  onError = {
                                      Toast.makeText(
                                          context,
                                          "Etwas ist schief gegangen!",
                                          Toast.LENGTH_SHORT
                                      ).show()
                                  }
                              )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(35.dp)
                    ) {
                        Text(text = "Chat beenden", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}