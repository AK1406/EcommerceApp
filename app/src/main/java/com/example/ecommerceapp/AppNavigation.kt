package com.example.ecommerceapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.home.HomeScreen
import com.example.ecommerceapp.registration.AuthScreen
import com.example.ecommerceapp.registration.LoginScreen
import com.example.ecommerceapp.registration.SignUpScreen
import com.example.ecommerceapp.viewmodel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val isLoggedIn = Firebase.auth.currentUser != null
    val firstScreen = if (isLoggedIn) "home" else "auth"
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = firstScreen, builder = {
        composable("auth") {
            AuthScreen(modifier, navController, authViewModel)
        }

        composable("login") {
            LoginScreen(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignUpScreen(modifier, navController, authViewModel)
        }
        composable("home") {
            HomeScreen(modifier, navController, authViewModel)
        }
    })
}