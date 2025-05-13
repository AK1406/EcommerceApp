package com.example.ecommerceapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecommerceapp.appUtils.AppUtils
import com.example.ecommerceapp.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CartItems(modifier: Modifier = Modifier, productId: String?, qty: Long) {
    System.out.println("productId: $productId, $qty")

    val product = remember { mutableStateOf(ProductModel()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("stocks").collection("products")
            .document("$productId").get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(ProductModel::class.java)
                    if (result != null) {
                        product.value = result
                        System.out.println("${product.value}")
                    }
                }

            }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        //.background(Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(4.dp)
        //colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = product.value.images.firstOrNull(),
                    contentDescription = "${product.value.title}",
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(80.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "${product.value.title}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text("₹${product.value.actualPrice}")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {
                                    AppUtils.RemoveItemFromCart(productId, context)
                                }) {
                                    Text("-")
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(qty.toString())
                                Spacer(modifier = Modifier.width(10.dp))
                                IconButton(onClick = {
                                    if (qty <= 4) {
                                        AppUtils.AddItemToCart(productId, context)
                                    }
                                }) {
                                    Text("+")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            AppUtils.RemoveItemFromCart(
                                productId,
                                context,
                                true
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "remove item from cart"
                            )
                        }
                    }
                }
            }
        }

    }
    Spacer(modifier = Modifier.height(8.dp))

}