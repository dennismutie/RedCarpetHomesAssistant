package com.example.redcarpethomesassistant.ui.theme.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redcarpethomesassistant.R
import com.example.redcarpethomesassistant.navigation.ROUT_HOME
import com.example.redcarpethomesassistant.navigation.ROUT_PROFILE
import com.example.redcarpethomesassistant.navigation.ROUT_PROPERTYLIST
import com.example.redcarpethomesassistant.navigation.ROUT_CHATBOT
import com.example.redcarpethomesassistant.navigation.ROUT_NOTIFICATION
import com.example.redcarpethomesassistant.navigation.ROUT_NOTIFICATION

@Composable
fun DashboardScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8B0000)) // Dark red background
    ) {
        // Red Carpet Homes icon (top center)
        Image(
            painter = painterResource(id = R.drawable.red_carpet_icon),
            contentDescription = "Red Carpet Homes Icon",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(150.dp)
                .align(Alignment.TopCenter)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(top = 34.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit,
            alpha = 0.8f
        )

        // Chatbot Icon (top left)
        IconButton(
            onClick = { navController.navigate(ROUT_CHATBOT) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFD700)) // Gold background
        ) {
            Image(
                painter = painterResource(id = R.drawable.chatbot_icon), // <-- Your drawable icon
                contentDescription = "Chatbot",
                modifier = Modifier.size(28.dp)
            )
        }

        // Profile Icon (top right)
        IconButton(
            onClick = { navController.navigate(ROUT_PROFILE) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFD700)) // Gold background
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color(0xFF8B0000),
                modifier = Modifier.size(32.dp)
            )
        }

        // Notifications Icon (bottom left)
        IconButton(
            onClick = { navController.navigate(ROUT_NOTIFICATION) },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFD700)) // Gold background
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color(0xFF8B0000),
                modifier = Modifier.size(32.dp)
            )
        }

        // Center content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "The Red Carpet Homes",
                fontSize = 28.sp,
                color = Color(0xFFFFD700),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Which dream fits you best?",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { navController.navigate("$ROUT_HOME?type=rental") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700))
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Rent an Apartment",
                            fontSize = 18.sp,
                            color = Color(0xFF8B0000),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { navController.navigate("$ROUT_PROPERTYLIST?type=land") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700))
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Buy Land & Invest",
                            fontSize = 18.sp,
                            color = Color(0xFF8B0000),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Land/Building image at bottom
        Image(
            painter = painterResource(id = R.drawable.land_building),
            contentDescription = "Land Preview",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )
    }
}