package com.example.ecommerceapp.navScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.components.Banners
import com.example.ecommerceapp.components.Categories
import com.example.ecommerceapp.components.HeaderView

@Composable
fun Dashboard(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeaderView(modifier)
        Spacer(modifier = Modifier.height(16.dp))
        Banners(modifier = Modifier.height(150.dp))
        Text("Categories", style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ))

        Spacer(modifier = Modifier.height(10.dp))
        Categories(modifier)
    }
}