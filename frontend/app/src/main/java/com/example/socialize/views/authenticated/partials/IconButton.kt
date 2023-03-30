package com.example.socialize.views.authenticated.partials

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    icon: ImageVector,
    description: String? = null,
    callback: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color(0xFF32425B), shape = RoundedCornerShape(16.dp))
            .clickable {
               if(callback !== null) {
                   callback()
               }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}