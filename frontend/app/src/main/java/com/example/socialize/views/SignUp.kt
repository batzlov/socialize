package com.example.socialize.views

import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialize.R
import com.example.socialize.models.AuthUser
import com.example.socialize.models.User
import com.example.socialize.ui.theme.Purple500
import com.example.socialize.viewModels.SignUpViewModel

@Composable
fun SignUp(navController: NavController) {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)

    val firstName = remember { mutableStateOf("Caroline") }
    val lastName = remember { mutableStateOf("Frei") }
    val email = remember { mutableStateOf("caroline-frei@web.de") }
    val password = remember { mutableStateOf("password") }

    val passwordVisible = remember { mutableStateOf(false) }

    val localContext = LocalContext.current
    val rememberScaffoldState = rememberScaffoldState()

    val vm: SignUpViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.background(Color.White),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp),
                    painter = painterResource(R.drawable.signup_image),
                    contentDescription = "Humans with smartphone.",
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Scaffold (
                modifier = Modifier.fillMaxSize(),
                scaffoldState = rememberScaffoldState
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(
                            topStart = 25.dp,
                            topEnd = 25.dp
                        ))
                        .background(Color.LightGray)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Socialize",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Dein Leben jetzt sozialer.",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = firstName.value,
                            onValueChange = { firstName.value = it },
                            label = { Text(text = "Vorname") },
                            placeholder = { Text(text = "Vorname") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        OutlinedTextField(
                            value = lastName.value,
                            onValueChange = { lastName.value = it },
                            label = { Text(text = "Nachname") },
                            placeholder = { Text(text = "Nachname") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            label = { Text(text = "E-Mail") },
                            placeholder = { Text(text = "E-Mail") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        passwordVisible.value = !passwordVisible.value
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.eye_icon),
                                        contentDescription = "Passwort",
                                        tint = if (passwordVisible.value) Purple500 else Color.Gray
                                    )
                                }
                            },
                            label = { Text(text = "Passwort") },
                            placeholder = { Text(text = "Passwort") },
                            singleLine = true,
                            visualTransformation = if (passwordVisible.value)
                                VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            if (email.value.isEmpty()) {
                                Toast.makeText(
                                    localContext,
                                    "E-Mail fehlerhaft!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (firstName.value.isEmpty()) {
                                Toast.makeText(
                                    localContext,
                                    "Vorname fehlerhaft!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (lastName.value.isEmpty()) {
                                Toast.makeText(
                                    localContext,
                                    "Nachname fehlerhaft!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (password.value.isEmpty()) {
                                Toast.makeText(
                                    localContext,
                                    "Passwort fehlerhaft!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val user: User = User(
                                    firstName.value,
                                    lastName.value,
                                    email.value,
                                    password.value,
                                    isAdmin = false
                                )
                                val createdUser: User? = vm.signUpSync(user)

                                if(createdUser != null) {
                                    navController.navigate("sign_in")
                                } else {
                                    // FIXME: handle failed request
                                    Log.d("MESSAGE", "Request was failure")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "Registrieren", fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.padding(15.dp))
                    Text(
                        text = "Du hast schon ein Konto?",
                        modifier = Modifier.clickable {
                            navController.navigate("sign_in")
                        }
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}