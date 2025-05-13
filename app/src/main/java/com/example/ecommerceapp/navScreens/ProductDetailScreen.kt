package com.example.ecommerceapp.navScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecommerceapp.appUtils.AppUtils
import com.example.ecommerceapp.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun ProductDetailScreen(modifier: Modifier = Modifier, productId: String) {

    var product by remember { mutableStateOf(ProductModel()) }
    var imageList by remember { mutableStateOf<List<String>>(emptyList()) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("stocks")
            .collection("products")
            .document("$productId").get().addOnCompleteListener {
                product = it.result.toObject(ProductModel::class.java)!!
            }
    }

    imageList = product.images

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "${product.title}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))

        val pagerState = rememberPagerState(0) { imageList.size }
        HorizontalPager(
            state = pagerState,
            pageSpacing = 24.dp
        ) {
            AsyncImage(
                model = imageList[it],
                contentDescription = "product image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        DotsIndicator(
            dotCount = imageList.size,
            type = ShiftIndicatorType(
                DotGraphic(
                    color = MaterialTheme.colorScheme.primary,
                    size = 6.dp
                )
            ),
            pagerState = pagerState
        )

        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "₹${product.price}",
                fontSize = 12.sp,
                style = TextStyle(textDecoration = TextDecoration.LineThrough)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = "₹${product.actualPrice}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {

            }) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "wishlist")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { AppUtils.AddItemToCart(productId, context) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Add to cart", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Product description:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Text("${product.description}", fontSize = 12.sp)
        Spacer(modifier = Modifier.height(15.dp))
        if (product.otherDetails.isNotEmpty()) {
            Text("Other product details:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))
            product.otherDetails.forEach { (key, value) ->
                Row {
                    Text("$key: ", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text("$value", fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}