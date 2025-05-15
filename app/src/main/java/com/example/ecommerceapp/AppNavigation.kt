package com.example.ecommerceapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.home.HomeScreen
import com.example.ecommerceapp.model.ProductModel
import com.example.ecommerceapp.navScreens.CategoryProductScreen
import com.example.ecommerceapp.navScreens.CheckoutScreen
import com.example.ecommerceapp.navScreens.ProductDetailScreen
import com.example.ecommerceapp.registration.AuthScreen
import com.example.ecommerceapp.registration.LoginScreen
import com.example.ecommerceapp.registration.SignUpScreen
import com.example.ecommerceapp.viewmodel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val isLoggedIn = Firebase.auth.currentUser != null
    val firstScreen = if (isLoggedIn) "home" else "auth"
    val navController = rememberNavController()
    GlobalNavigation.navController = navController
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

        composable("category-product/{categoryId}") {
            val catId = it.arguments?.getString("categoryId")
            CategoryProductScreen(modifier,catId?:"")
        }
        composable("product-detail/{productId}") {
            val productId = it.arguments?.getString("productId")
            if (productId != null) {
                ProductDetailScreen(modifier,productId)
            }
        }
        composable("checkout"){
            CheckoutScreen(modifier)
        }
    })
}

object GlobalNavigation{
    lateinit var navController: NavController
}