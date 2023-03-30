package com.example.socialize

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import com.example.socialize.ui.theme.SocializeTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.socialize.util.SocketHandler
import com.example.socialize.views.SignIn
import com.example.socialize.views.SignUp
import com.example.socialize.views.authenticated.*
import com.google.gson.Gson
import androidx.core.app.ActivityCompat
import com.example.socialize.util.LocalStorage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var ls: LocalStorage = LocalStorage()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocializeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // precise location access granted
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location : Location? ->
                            handleLastKnownLocation(location)
                        }
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // only approximate location access granted
                } else -> {
                    // no location access granted
                }
            }
        }

        // request the needed permissions from the user
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            )
        )
    }

    private fun handleLastKnownLocation(location: Location?) {
        // got last known location, in some rare situations this can be null -> happens when the user has never used location services before
        if (location == null) {
            ls.saveValue("location", "-1")
        } else {
            val addresses: List<Address>?
            val geoCoder = Geocoder(applicationContext, Locale.getDefault())
            addresses = geoCoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )

            ls.saveValue("latitude", location.latitude.toString())
            ls.saveValue("longitude", location.longitude.toString())

            if (addresses != null && addresses.isNotEmpty()) {
                val address: String = addresses[0].getAddressLine(0)

                ls.saveValue("location", address)
            }
        }
    }
}

@Composable
fun App() {
    val rememberNavController = rememberNavController()
    var startDestination: String = "sign_in";

    val ls: LocalStorage = LocalStorage()
    val authToken = ls.getValue("authToken")
    val userId = ls.getValue("userId")
    if(authToken !== "") {
        startDestination = "authenticated"
    }

    NavHost(
        navController = rememberNavController,
        startDestination = startDestination,
        builder = {
            composable("sign_in", content = { SignIn(navController = rememberNavController) })
            composable("sign_up", content = { SignUp(navController = rememberNavController) })
            composable("home", content = { Home(navController = rememberNavController) })
            // FIXME: there seems to be a bug when using a dynamic routes as start destination, the authenticated route is a workaround
            composable("authenticated") {
                LaunchedEffect(this) {
                    rememberNavController.navigate("socialize/${userId}/${authToken}")
                }
            }
            composable(
                "socialize/{userId}/{authToken}",
                arguments = listOf(
                    navArgument("userId") {
                        type = NavType.IntType
                    },
                    navArgument("authToken") {
                        type = NavType.StringType
                    }
                ),
                content = {
                    val userId: Int = it.arguments?.getInt("userId")!!
                    val authToken: String = it.arguments?.getString("authToken")!!

                    Socialize(nc = rememberNavController, userId, authToken)
                }
            )
            composable(
                "chat/{id}/{chatName}/{isPrivateChat}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    },
                    navArgument("chatName") {
                        type = NavType.StringType
                    },
                    navArgument("isPrivateChat") {
                        type = NavType.BoolType
                    }
                ),
                content = {
                    val id: Int = it.arguments?.getInt("id")!!
                    val chatName: String = it.arguments?.getString("chatName")!!
                    val isPrivateChat: Boolean = it.arguments?.getBoolean("isPrivateChat")!!
                    Chat(navController = rememberNavController, chatId = id, chatName = chatName, isPrivateChat)
                }
            )
            composable("locations", content = { Locations(navController = rememberNavController) })
            composable("meet", content = { Meet(navController = rememberNavController) })
        }
    )
}