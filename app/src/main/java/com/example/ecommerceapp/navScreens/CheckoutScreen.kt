package com.example.ecommerceapp.navScreens

import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.appUtils.AppUtils
import com.example.ecommerceapp.model.ProductModel
import com.example.ecommerceapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CheckoutScreen(modifier: Modifier = Modifier) {

    val userModel = remember { mutableStateOf(UserModel()) }
    val productList = remember { mutableStateListOf(ProductModel()) }
    val subTotalPrice = remember { mutableStateOf(0f) }
    val amtToPay = remember { mutableStateOf(0f) }

    fun calculateSubTotal() {
        productList.forEach {
            if (it.actualPrice?.isNotEmpty() == true) {
                val qty = userModel.value.cartItems[it.id] ?: 0
                val actual_price = it.actualPrice.replace(",", "").trim()
                subTotalPrice.value += (actual_price.toFloat() * qty)
            }
        }
        amtToPay.value =
            "%.2f".format(subTotalPrice.value - AppUtils.getDiscountValue() + AppUtils.getTaxValue())
                .toFloat()
    }



    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result

                        Firebase.firestore.collection("data").document("stocks")
                            .collection("products")
                            .whereIn("id", userModel.value.cartItems.keys.toList())
                            .get().addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val products = it.result.toObjects(ProductModel::class.java)
                                    if (products.isNotEmpty()) {
                                        productList.addAll(products)
                                        calculateSubTotal()
                                    }
                                }
                            }
                    }
                }
            }
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Checkout",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Delivering to:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text("${userModel.value.name}", fontSize = 14.sp)
        Text(
            text = "${userModel.value.address}", fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))
        RowCheckoutItems("Subtotal:","₹ ${subTotalPrice.value}")
        Spacer(modifier = Modifier.height(5.dp))
        RowCheckoutItems("Discount(-):", "₹ ${AppUtils.getDiscountValue()}")
        Spacer(modifier = Modifier.height(5.dp))
        RowCheckoutItems( "Tax(+):", "₹ ${AppUtils.getTaxValue()}")
        Spacer(modifier = Modifier.height(15.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "To Pay",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "₹ ${amtToPay.value}", fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
        )
    }
}


@Composable
fun RowCheckoutItems(heading: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = heading,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(text = "$value", fontSize = 16.sp)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview(modifier: Modifier = Modifier) {
    CheckoutScreen(modifier)
}