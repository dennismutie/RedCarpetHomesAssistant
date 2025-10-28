package com.example.redcarpethomesassistant.ui.theme.screens.blog


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.example.redcarpethomesassistant.navigation.ROUT_DASHBOARD
import com.example.redcarpethomesassistant.navigation.ROUT_PROFILE // Assume route for profile screen

data class BlogPost(
    val title: String,
    val excerpt: String,
    val date: String, // e.g., "October 09, 2025"
    val imageRes: Int,
    val fullContent: String? = null // For future detail expansion
)

@Composable
fun BlogScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8B0000)) // Dark red, matching theme
    ) {
        // Background Red Carpet Homes Icon (centered, slightly more visible)
        Image(
            painter = painterResource(id = R.drawable.red_carpet_icon),
            contentDescription = "Background Red Carpet Homes Logo",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit,
            alpha = 0.3f
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 80.dp) // Space for floating back button
        ) {
            item {
                // Header: Logo and Title
                Image(
                    painter = painterResource(id = R.drawable.red_carpet_icon),
                    contentDescription = "Red Carpet Homes Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Market Insights",
                    fontSize = 28.sp,
                    color = Color(0xFFFFD700), // Gold
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Stay updated with the latest trends in Kenya's real estate market.",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(
                listOf(
                    BlogPost(
                        "2025 Nairobi Property Boom",
                        "Expect 15% growth in Kilimani with new infrastructure projects boosting demand for premium apartments and land.",
                        "October 09, 2025",
                        R.drawable.blog_nairobi
                    ),
                    BlogPost(
                        "Top Investment Tips for Kitengela",
                        "Why ready-titled plots offer the best ROI in emerging areas like Kitengela, with flexible payment plans.",
                        "September 25, 2025",
                        R.drawable.blog_kitengela
                    ),
                    BlogPost(
                        "Lavington Villas: A Smart Buy?",
                        "Analyzing the 20% price growth in Lavington and why exclusive homes here are future-proof investments.",
                        "August 15, 2025",
                        R.drawable.lavington
                    ),
                    BlogPost(
                        "Passive Income from Watamu Beach Plots",
                        "Discover coastal opportunities in Watamu for vacation rentals yielding up to 18% annual returns.",
                        "July 20, 2025",
                        R.drawable.watamu // Suggest adding this drawable
                    )
                )
            ) { post ->
                BlogPostCard(post, navController)
            }
        }

        // Floating Back to Profile Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(
                onClick = { navController.navigate(ROUT_PROFILE) { popUpTo(ROUT_PROFILE) { inclusive = true } } }, // Pop back to profile
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
            ) {
                Text("Back to Profile", color = Color(0xFF8B0000), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun BlogPostCard(post: BlogPost, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // TODO: Navigate to BlogDetailScreen with post ID or content
                // For now, just log or show snackbar; add ROUT_BLOG_DETAIL later
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = post.imageRes),
                contentDescription = post.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = post.title,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.excerpt,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Start,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.date,
                fontSize = 12.sp,
                color = Color(0xFFFFD700),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = {
                    // TODO: Navigate to detail
                }
            ) {
                Text("Read More", color = Color(0xFFFFD700))
            }
        }
    }
}