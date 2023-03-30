package com.example.socialize.views.authenticated.partials

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.socialize.R
import com.example.socialize.ui.theme.Purple500

@Composable
fun TopBar(
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    starIconDescription: String? = null,
    endIconDescription: String? = null,
    title: String,
    hasSearchbar: Boolean = false,
    searchQuery: String? = null,
    setSearchQuery: ((String) -> Unit)? = null
) {
    val (searchbarIsVisible, setSearchbarIsVisible) = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
        horizontalArrangement = if (startIcon == null || endIcon == null) Arrangement.SpaceBetween else Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(searchbarIsVisible && hasSearchbar) {
            OutlinedTextField(
                value = searchQuery!!,
                onValueChange = setSearchQuery!!,
                placeholder = {
                    Text(
                        "Name des Chats..",
                        color = Color.White
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    textColor = Color.White
                )
            )
            IconButton(
                icon = Icons.Outlined.Close,
                "Deine Suche",
                callback = {
                    setSearchbarIsVisible(false)
                    setSearchQuery("")
                }
            )
        } else {
            if (startIcon != null) {
                IconButton(
                    icon = Icons.Outlined.Menu,
                    starIconDescription
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            if (endIcon != null) {
                IconButton(
                    icon = Icons.Outlined.Search,
                    endIconDescription,
                    callback = {
                        setSearchbarIsVisible(true)
                    }
                )
            }
        }
    }
}