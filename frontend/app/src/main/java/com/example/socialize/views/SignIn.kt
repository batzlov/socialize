package com.example.socialize.views

import android.content.Context
import android.content.SharedPreferences
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
// import androidx.compose.foundation.layout.R
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
import com.example.socialize.models.AuthTokenData
import com.example.socialize.models.AuthUser
import com.example.socialize.ui.theme.Purple500
import com.example.socialize.viewModels.SignInViewModel


@Composable
fun SignIn(navController: NavController) {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)

    val email = remember { mutableStateOf("caroline-frei@web.de") }
    val password = remember { mutableStateOf("password") }

    val passwordVisible = remember { mutableStateOf(false) }

    val localContext = LocalContext.current
    val rememberScaffoldState = rememberScaffoldState()

    val vm: SignInViewModel = viewModel()

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
                    painter = painterResource(R.drawable.signin_image),
                    contentDescription = "Girl with a laptop.",
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
                            } else if (password.value.isEmpty()) {
                                Toast.makeText(
                                    localContext,
                                    "Passwort fehlerhaft!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                var latitude: Float = 0f
                                var longitude: Float = 0f

                                if(vm.ls.getValue("location").isNotEmpty() && vm.ls.getValue("location") != "-1") {
                                    latitude = vm.ls.getValue("latitude").toFloat()
                                    longitude = vm.ls.getValue("longitude").toFloat()
                                }

                                val user: AuthUser = AuthUser(
                                    email.value,
                                    password.value,
                                    latitude,
                                    longitude
                                )
                                val authTokenData: AuthTokenData? = vm.signInSync(user)

                                if(authTokenData != null) {
                                    vm.ls.saveValue("authToken", authTokenData.token)
                                    vm.ls.saveValue("userId", authTokenData.userId.toString())
                                    vm.ls.saveValue("zip", vm.getUserPostalCode())
                                    navController.navigate("socialize/${authTokenData.userId}/${authTokenData.token}")
                                } else {
                                    Toast.makeText(
                                        localContext,
                                        "Etwas ist schief gelaufen! Versuche es erneut.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "Anmelden", fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.padding(15.dp))
                    Text(
                        text = "Noch kein Konto?",
                        modifier = Modifier.clickable {
                            navController.navigate("sign_up")
                        }
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}