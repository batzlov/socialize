package com.example.socialize.views.authenticated.partials

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
fun ChatItem(
    groupChatId: Int,
    userName: String,
    lastMessage: String,
    unreadMessageCount: Int,
    profile: String,
    lastMessageTime: String,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate("chat/$groupChatId/$userName/false")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                //.height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(profile),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = userName, color = MaterialTheme.colors.primary, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = lastMessage, color = MaterialTheme.colors.secondary.copy(alpha = .6f), fontSize = 14.sp)
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = lastMessageTime, color = MaterialTheme.colors.secondary.copy(alpha = .6f), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(MaterialTheme.colors.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = unreadMessageCount.toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
        Divider(color = Color(0xFF32425B), thickness = 1.dp)
    }
}