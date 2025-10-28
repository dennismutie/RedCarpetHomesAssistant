// Updated LoginScreen.kt - Added missing imports for KeyboardOptions and KeyboardType
package com.example.redcarpethomesassistant.ui.theme.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.example.redcarpethomesassistant.R
import com.example.redcarpethomesassistant.data.AuthRepository
import com.example.redcarpethomesassistant.data.SharedReminders
import com.example.redcarpethomesassistant.navigation.ROUT_REGISTER
import com.example.redcarpethomesassistant.navigation.ROUT_DASHBOARD
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var pass by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val authRepo = remember { AuthRepository() }
    val coroutineScope = rememberCoroutineScope()

    // Refined color palette for a more luxurious feel
    val primaryRed = Color(0xFF8B0000) // Deep red
    val accentGold = Color(0xFFFFD700) // Gold
    val backgroundGold = Color(0xFFFFD700) // Gold background
    val textGray = Color(0xFF666666)
    val white = Color.White
    val black = Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGold),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp) // Reduced overall spacing for tighter layout
        ) {
            // Red Carpet Homes Logo with white rounded background for contrast
            Image(
                painter = painterResource(id = R.drawable.red_carpet_icon), // Ensure this exists
                contentDescription = "Red Carpet Homes Logo",
                modifier = Modifier
                    .size(120.dp) // Increased size for visibility
                    .padding(bottom = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)) // White background for contrast
                    .padding(4.dp), // Padding inside the background
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Welcome Back To Red Carpet Homes !",
                color = primaryRed,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            Text(
                text = "Discover Exquisite Luxury Living & Exclusive Premier Properties",
                color = textGray,
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address", color = textGray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = white,
                    unfocusedContainerColor = white,
                    focusedIndicatorColor = primaryRed,
                    unfocusedIndicatorColor = textGray,
                    focusedLabelColor = primaryRed,
                    unfocusedLabelColor = textGray,
                    focusedTextColor = black,
                    unfocusedTextColor = black
                ),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Password", color = textGray) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = white,
                    unfocusedContainerColor = white,
                    focusedIndicatorColor = primaryRed,
                    unfocusedIndicatorColor = textGray,
                    focusedLabelColor = primaryRed,
                    unfocusedLabelColor = textGray,
                    focusedTextColor = black,
                    unfocusedTextColor = black
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) android.R.drawable.ic_menu_view else android.R.drawable.ic_menu_close_clear_cancel
                        ),
                        contentDescription = "Toggle Password Visibility",
                        modifier = Modifier
                            .clickable { passwordVisible = !passwordVisible }
                            .padding(4.dp)
                    )
                }
            )

            // Forgot Password link (left-aligned, just below password)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp), // Minimal top padding for tight spacing
                horizontalArrangement = Arrangement.Start
            ) {
                TextButton(
                    onClick = {
                        val emailStr = email.text.trim()
                        if (emailStr.isNotEmpty()) {
                            coroutineScope.launch {
                                val result = authRepo.sendPasswordReset(emailStr)
                                if (result.isSuccess) {
                                    Toast.makeText(context, "Password reset email sent!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Failed to send reset email: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please enter your email first", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = primaryRed,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // No spacer here for direct placement below forgot password
            Button(
                onClick = {
                    val emailStr = email.text.trim()
                    val passStr = pass.text.trim()
                    if (emailStr.isNotEmpty() && passStr.isNotEmpty()) {
                        coroutineScope.launch {
                            val result = authRepo.login(emailStr, passStr)
                            if (result.isSuccess) {
                                val userId = result.getOrNull()!!
                                // Load user-specific reminders
                                val reminders = authRepo.loadUserReminders(userId)
                                SharedReminders.setUser(userId, reminders)
                                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                                navController.navigate(ROUT_DASHBOARD) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Login failed: ${result.exceptionOrNull()?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryRed),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("Log In", color = white, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            // No spacer here for direct placement below login button
            // Register link as clickable row for better touch target
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(ROUT_REGISTER) }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? Register here",
                    color = primaryRed,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}