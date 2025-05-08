package com.example.ecommerceapp.navScreens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CategoryProductScreen(modifier: Modifier = Modifier,catId:String) {
    Text("Category product: $catId")
}