package com.example.ecommerceapp.registration

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.AuthState
import com.example.ecommerceapp.R
import com.example.ecommerceapp.viewmodel.AuthViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel? = null
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authstate = authViewModel?.authState?.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authstate?.value) {
        when (authstate?.value) {
            is AuthState.Authenticated  -> {navController.navigate("home"){
            popUpTo("auth"){inclusive = true}
        }}
            is AuthState.Error -> Toast.makeText(
                context,
                (authstate.value as AuthState.Error).msg,
                Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Welcome back!",
            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Sign in your account",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.sign_img),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentDescription = "login banner"
        )
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text("Email")
        }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { authViewModel?.login(email, password) }, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            enabled = authstate?.value != AuthState.Loader
        ) {
            Text("Login")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()

    LoginScreen(
        navController = navController,
        authViewModel = null
    )
}
