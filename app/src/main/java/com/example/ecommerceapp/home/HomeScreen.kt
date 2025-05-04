package com.example.ecommerceapp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommerceapp.AuthState
import com.example.ecommerceapp.model.NavItemModel
import com.example.ecommerceapp.navScreens.CartScreen
import com.example.ecommerceapp.navScreens.Dashboard
import com.example.ecommerceapp.navScreens.FavouriteScreen
import com.example.ecommerceapp.navScreens.ProfileScreen
import com.example.ecommerceapp.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val navItemList = listOf(
        NavItemModel("Home", Icons.Default.Home),
        NavItemModel("Cart", Icons.Default.ShoppingCart),
        NavItemModel("Favourite", Icons.Default.Favorite),
        NavItemModel("Home", Icons.Default.Person),
    )
    var selectedItemIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {selectedItemIndex = index},
                        icon = {
                            navItem.icon?.let {
                                Icon(
                                    imageVector = it,
                                    contentDescription = navItem.label
                                )
                            }
                        },
                        label = { navItem.label?.let { Text(it) } }
                    )
                }
            }
        }
            ) {
            ContentScreen(modifier = modifier.padding(it), selectedItemIndex)
        }

        }

        @Composable
        fun ContentScreen(
            modifier: Modifier = Modifier,selectedIdx:Int
        ) {
          when(selectedIdx){
              0-> Dashboard(modifier)
              1-> CartScreen(modifier)
              2-> FavouriteScreen(modifier)
              3-> ProfileScreen(modifier)
          }

        }