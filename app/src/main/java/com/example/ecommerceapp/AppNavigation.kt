package com.example.ecommerceapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.registration.AuthScreen
import com.example.ecommerceapp.registration.LoginScreen
import com.example.ecommerceapp.registration.SignUpScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "auth", builder = {
        composable("auth") {
            AuthScreen(modifier,navController)
        }

        composable("login") {
            LoginScreen(modifier,navController)
        }
        composable("signup") {
            SignUpScreen(modifier,navController)
        }
    })
}