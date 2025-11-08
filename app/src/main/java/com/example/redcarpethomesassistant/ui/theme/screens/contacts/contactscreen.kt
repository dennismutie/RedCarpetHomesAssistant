package com.example.redcarpethomesassistant.ui.theme.screens.contacts

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redcarpethomesassistant.R
import com.example.redcarpethomesassistant.navigation.ROUT_DASHBOARD

@Composable
fun ContactScreen(navController: NavController) {
    val context = LocalContext.current
    val phoneNumber = "0706127197"
    val email = "info@theredcarpethomes.co.ke"
    val whatsappUrl = "https://wa.me/254$phoneNumber?text=Interested in properties from Red Carpet Homes"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8B0000)) // Dark red
            .padding(16.dp)
    ) {
        // Red Carpet Icon Background at the Top, Displayed in Full
        Image(
            painter = painterResource(id = R.drawable.red_carpet_icon), // Assume this is the icon
            contentDescription = "Red Carpet Icon",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Fit // Ensures the full image is displayed
        )

        // Contact Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp), // Offset to match icon height
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Contact Us",
                fontSize = 28.sp,
                color = Color(0xFFFFD700), // Gold
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = "Get in touch for site visits, pricing, or details",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Call Button (Boxed Style)
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phoneNumber")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B0000)) // Darker red box
            ) {
                Text("Call: $phoneNumber", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // SMS Button (Boxed Style)
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("sms:$phoneNumber?body=Interested in Red Carpet Homes properties")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B0000)) // Darker red box
            ) {
                Text("Send SMS", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // WhatsApp Button (Boxed Style with WhatsApp green)
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl))
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)) // WhatsApp green
            ) {
                Text("WhatsApp Chat", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // Email Button (Updated Text)
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:$email?subject=Inquiry from Red Carpet Homes App&body=Interested in properties. Please provide details.")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B0000)) // Darker red box
            ) {
                Text("Send us an email", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // Back to Dashboard Button (Increased Size and Visibility)
            Button(
                onClick = { navController.navigate(ROUT_DASHBOARD) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp) // Maintained increased height
                    .padding(top = 32.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A0000)) // Darker red for contrast
            ) {
                Text("Back to Dashboard", color = Color(0xFFFFD700), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}