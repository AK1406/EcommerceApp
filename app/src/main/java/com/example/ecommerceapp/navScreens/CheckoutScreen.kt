package com.example.ecommerceapp.navScreens

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.model.ProductModel
import com.example.ecommerceapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CheckoutScreen(modifier: Modifier = Modifier) {

    val userModel = remember { mutableStateOf(UserModel()) }
    val productList = remember { mutableStateOf<List<ProductModel>>(emptyList()) }

    Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().uid!!)
        .get().addOnCompleteListener{
            if(it.isSuccessful){
                val result = it.result.toObject(UserModel::class.java)
                if(result != null){
                    userModel.value = result

                    Firebase.firestore.collection("data").document("stocks").collection("products")
                        //TODO: extract product
                }
            }
        }

    Column(
        modifier = Modifier
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
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "address", fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Subtotal:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "amount", fontSize = 16.sp, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Discount(-):",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "amount", fontSize = 16.sp, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Tax(+):",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "amount", fontSize = 16.sp, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "To Pay", fontSize = 10.sp)
        Text(
            text = "₹ 2,999", fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}