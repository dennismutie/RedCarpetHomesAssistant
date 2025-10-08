package com.example.redcarpethomesassistant.ui.theme.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
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

@Composable
fun DashboardScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8B0000)) // Dark red background
    ) {
        // Red Carpet Homes icon (top, larger for prominence)
        Image(
            painter = painterResource(id = R.drawable.red_carpet_icon), // Add to res/drawable
            contentDescription = "Red Carpet Homes Icon",
            modifier = Modifier
                .fillMaxWidth(0.8f) // Increased width proportion
                .height(150.dp) // Increased height
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit,
            alpha = 0.8f
        )

        // Content overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "The Red Carpet Homes",
                fontSize = 28.sp,
                color = Color(0xFFFFD700), // Gold
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Welcome Message (from website)
            Text(
                text = "Which dream fits you best?",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Cards with Goldish Color for Options
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { navController.navigate("$ROUT_HOME?type=rental") }, // Navigate to built apartments
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700)) // Goldish color
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Rent an Apartment",
                            fontSize = 18.sp,
                            color = Color(0xFF8B0000), // Red text
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { navController.navigate("$ROUT_PROPERTYLIST?type=land") }, // Navigate to land countrywide
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700)) // Goldish color
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Buy Land & Invest",
                            fontSize = 18.sp,
                            color = Color(0xFF8B0000), // Red text
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Profile Icon (top right, circular for avatar look)
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
                imageVector = Icons.Default.Person, // Material Design profile icon
                contentDescription = "Profile",
                tint = Color(0xFF8B0000), // Red tint
                modifier = Modifier.size(32.dp)
            )
        }

        // Land/Building Image at Bottom
        Image(
            painter = painterResource(id = R.drawable.land_building), // Add Kitengela land/building photo to res/drawable
            contentDescription = "Land Preview",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Increased height for prominence
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 0.dp, bottomStart = 0.dp)),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )
    }
}