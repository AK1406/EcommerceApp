package com.example.ecommerceapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ecommerceapp.GlobalNavigation
import com.example.ecommerceapp.model.CategoryModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun Categories(modifier: Modifier = Modifier) {

    val categoryList = remember { mutableStateOf<List<CategoryModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("stocks").collection("categories").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    categoryList.value = it.result.documents.mapNotNull { doc ->
                        doc.toObject(CategoryModel::class.java)
                    }
                }
            }
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(categoryList.value) { item ->
            categoryItem(modifier, item)
        }
    }
}


@Composable
fun categoryItem(modifier: Modifier = Modifier, item: CategoryModel) {

    Card(
        modifier = Modifier.size(100.dp)
            .clickable { GlobalNavigation.navController.navigate("category-product/${item.id}") },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = item.imgUrl, contentDescription = item.name,
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier.padding(5.dp,0.dp,5.dp,0.dp),
                text = item.name.toString(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}