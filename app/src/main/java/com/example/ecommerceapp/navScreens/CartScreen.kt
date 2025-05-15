package com.example.ecommerceapp.navScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.GlobalNavigation
import com.example.ecommerceapp.components.CartItems
import com.example.ecommerceapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CartScreen(modifier: Modifier = Modifier) {

    val userModel = remember { mutableStateOf(UserModel()) }

    DisposableEffect(key1 = Unit) {
      val listener =  Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener { it,_->
                if (it!= null) {
                    val result = it.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result
                    }
                }
            }
        onDispose {
            listener.remove()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Your cart", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(userModel.value.cartItems.toList(), key = {it.first}) { (productId, qty) ->
                CartItems(modifier, productId, qty)
            }
        }

        Button(
            onClick = { GlobalNavigation.navController.navigate("checkout") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Checkout", fontSize = 16.sp)
        }


    }
}