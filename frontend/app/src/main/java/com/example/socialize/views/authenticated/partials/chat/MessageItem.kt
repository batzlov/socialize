package com.example.socialize.views.authenticated.partials.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageItem(
    messageText: String,
    author: String,
    time: String,
    isOut: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = if (isOut) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isOut) MaterialTheme.colors.primary else Color(0xFF616161),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(
                    top = 16.dp,
                    bottom = 16.dp,
                    start = 24.dp,
                    end = 24.dp
                )
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = author,
                    fontSize = 12.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = messageText,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}