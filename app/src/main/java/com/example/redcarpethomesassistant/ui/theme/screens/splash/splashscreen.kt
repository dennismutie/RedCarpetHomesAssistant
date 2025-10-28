package com.example.redcarpethomesassistant.ui.theme.screens.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.example.redcarpethomesassistant.R
import com.example.redcarpethomesassistant.navigation.ROUT_DASHBOARD
import com.example.redcarpethomesassistant.navigation.ROUT_LOGIN

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3000) // 3-second delay for branding
        navController.navigate(ROUT_LOGIN) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8B0000)), // Dark red background matching app theme
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Red Carpet Homes Logo with white rounded background for contrast
        Image(
            painter = painterResource(id = R.drawable.red_carpet_icon),
            contentDescription = "Red Carpet Homes Logo",
            modifier = Modifier
                .size(200.dp) // Balanced size for splash visibility
                .clip(RoundedCornerShape(8.dp)) // Clip image to rounded shape
                .background(Color.White, shape = RoundedCornerShape(8.dp)) // White background for contrast
                .padding(4.dp), // Padding inside the background
            contentScale = androidx.compose.ui.layout.ContentScale.Fit
        )

        // Main App Title
        Text(
            text = "Welcome to Red Carpet Homes",
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFFFD700), // Gold color for premium feel
            modifier = Modifier.padding(top = 24.dp)
        )

        // Welcoming Note for Real Estate App
        Text(
            text = "Discover Premium Land & Exclusive Homes\nin Kenya's Prime Locations",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.9f), // Subtle white for readability
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp),
            lineHeight = 22.sp
        )

        // Subtle Tagline
        Text(
            text = "18+ Years of Excellence | Ready-Titled Plots",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFFFFD700).copy(alpha = 0.8f), // Faded gold
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}