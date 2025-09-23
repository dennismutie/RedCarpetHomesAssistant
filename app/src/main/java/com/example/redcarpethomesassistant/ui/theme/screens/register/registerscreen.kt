package com.example.redcarpethomesassistant.ui.theme.screens.register

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.example.redcarpethomesassistant.navigation.ROUT_DASHBOARD
import com.example.redcarpethomesassistant.navigation.ROUT_LOGIN

@Composable
fun RegisterScreen(navController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var pass by remember { mutableStateOf(TextFieldValue("")) }
    var confirmpass by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFD700)), // Gold background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Create an Account",
                color = Color(0xFF8B0000), // Dark red
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            )
            Text(
                text = "Sign up for exclusive listings",
                color = Color(0xFF8B0000),
                fontSize = 18.sp
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address", color = Color(0xFF8B0000)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF8B0000),
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            TextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Password", color = Color(0xFF8B0000)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF8B0000),
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            TextField(
                value = confirmpass,
                onValueChange = { confirmpass = it },
                label = { Text("Confirm Password", color = Color(0xFF8B0000)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF8B0000),
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            Button(
                onClick = {
                    val emailStr = email.text.trim()
                    val passStr = pass.text.trim()
                    val confirmPassStr = confirmpass.text.trim()
                    if (emailStr.isNotEmpty() && passStr.isNotEmpty() && passStr == confirmPassStr) {
                        auth.createUserWithEmailAndPassword(emailStr, passStr)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                                navController.navigate(ROUT_DASHBOARD)
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Registration failed: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Passwords do not match or fields empty", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Register", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            TextButton(
                onClick = { navController.navigate(ROUT_LOGIN) },
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = "Already have an account? Log in",
                    color = Color(0xFF8B0000),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}