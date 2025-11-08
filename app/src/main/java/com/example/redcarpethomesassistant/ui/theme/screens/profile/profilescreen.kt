package com.example.redcarpethomesassistant.ui.theme.screens.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.redcarpethomesassistant.navigation.ROUT_PROPERTYLIST // Assume route for portfolio

// Team Member Data Class
data class TeamMember(
    val name: String,
    val role: String,
    val imageRes: Int
)

// Project Data Class
data class Project(
    val name: String,
    val stat: String,
    val imageRes: Int
)

// Testimonial Data Class
data class Testimonial(
    val quote: String,
    val author: String,
    val rating: Int
)

@Composable
fun TeamMemberCard(member: TeamMember) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = member.imageRes),
                contentDescription = member.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = member.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = member.role,
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun ProjectCard(project: Project, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { navController.navigate(ROUT_PROPERTYLIST) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = project.imageRes),
                contentDescription = project.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = project.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = project.stat,
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun TestimonialCard(testimonial: Testimonial) {
    Card(
        modifier = Modifier
            .width(200.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = testimonial.quote,
                fontSize = 12.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "– ${testimonial.author}",
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(testimonial.rating) { _ ->
                    Icon(
                        painter = painterResource(id = R.drawable.ic_redcarpet), // Add star icon to drawable
                        contentDescription = "Star",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8B0000)) // Dark red
    ) {
        // Background Red Carpet Homes Icon (centered, slightly more visible)
        Image(
            painter = painterResource(id = R.drawable.red_carpet_icon), // Ensure this exists in res/drawable
            contentDescription = "Background Red Carpet Homes Logo",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit,
            alpha = 0.3f // Increased visibility
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Enable scrolling for the entire content
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Red Carpet Homes Icon (top, larger) with white rounded background
            Image(
                painter = painterResource(id = R.drawable.red_carpet_icon), // Ensure this exists in res/drawable
                contentDescription = "Red Carpet Homes Logo",
                modifier = Modifier
                    .size(150.dp) // Larger size
                    .padding(bottom = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)) // White background for contrast
                    .padding(4.dp) // Padding inside the background
                    .clip(RoundedCornerShape(8.dp)), // Clip to rounded shape
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Red Carpet Homes",
                fontSize = 28.sp,
                color = Color(0xFFFFD700), // Gold
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            // About Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "About Us",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "We are Red Carpet Homes, Kenya's leading real estate developer with 18+ years of excellence. We specialize in land sales of premium, ready-titled plots and exclusive homes in high-growth Nairobi areas like Kilimani and Lavington—delivering 18% ROI and seamless luxury living through flexible plans and prime amenities.",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Services Offered
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Services",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listOf("Land Sales", "Rentals", "Investments")) { service ->
                            AssistChip(
                                onClick = { },
                                label = { Text(service, fontSize = 12.sp) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(0xFF8B0000),
                                    labelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Locations Served
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Green.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Locations",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Kitengela, Juja Farm, Watamu, Diani, Riruta, Roysambu, Kiambu, Fedha, Kilimani, Lavington",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Key Stats/Features
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Key Features",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "• 7+ Successfully Finished Projects in Kilimani & Lavington",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "• 18+ Years in the Market",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "• 18% Return on Investment",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "• 20% Average Price Growth Over Last 3 Years",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "• Ready Title Deeds with Genuine Documentation",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "• Flexible Payment Plans with Affordable Installments",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = "• Proximity to Amenities: Malls, Schools, Hospitals",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }

            // Team/Leadership Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Our Team",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(listOf(
                            TeamMember("Rachael Ndunge", "CEO", R.drawable.red_carpet_events),
                            TeamMember("Rachael", "Sales Manager", R.drawable.team_agent),
                            TeamMember("Rachael", "Agent", R.drawable.team_agent)
                        )) { member ->
                            TeamMemberCard(member)
                        }
                    }
                }
            }

            // Portfolio/Featured Projects
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Featured Projects",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            listOf(
                                Project(
                                    "Kilimani Apartments",
                                    "18% ROI",
                                    R.drawable.kilimani_project
                                ),
                                Project("Lavington Villas", "20% Growth", R.drawable.lavington)
                            )
                        ) { project ->
                            ProjectCard(project, navController)
                        }
                        }
                        TextButton(
                            onClick = { navController.navigate(ROUT_PROPERTYLIST) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("View All Projects", color = Color.White)
                        }
                    }
                }

                // Testimonials Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "What Our Clients Say",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(listOf(
                                Testimonial("Excellent service and quick transactions on my plot purchase!", "Dennis Mutie", 5),
                                Testimonial("Highly recommend for secure land investments in Kitengela.", "Lawrence", 5),
                                Testimonial("Smooth process and ready titles – exceeded expectations!", "Shadrack M", 5),
                                Testimonial("Great ROI on my Lavington property. Professional team.", "Stephen L", 5),
                                Testimonial("Flexible payments made it easy to buy my dream home.", "William N", 5),
                                Testimonial("Prime locations and amazing amenities. Truly luxurious.", "Ann W", 5),
                                Testimonial("Best real estate partner for Watamu beachfront deals.", "Chemutai C", 5),
                                Testimonial("Reliable documentation and fast site visits.", "Rosemary Y", 5),
                                Testimonial("Sold 50 plots in Kitengela – Happy Investor!", "John K.", 5),
                                Testimonial("Great service and ready titles!", "Mary A.", 5)
                            )) { testimonial ->
                                TestimonialCard(testimonial)
                            }
                        }
                    }
                }

                // Events Section with Uploaded Image
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Our Events",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        // Uploaded Image (add to res/drawable as R.drawable.red_carpet_event)
                        Image(
                            painter = painterResource(id = R.drawable.red_carpet_events),
                            contentDescription = "Red Carpet Homes Event",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Join us at our events to learn about passive income opportunities with Red Carpet Homes!",
                            fontSize = 14.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                // Contact Details
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Contact Us",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:info@theredcarpethomes.co.ke")
                                    putExtra(Intent.EXTRA_SUBJECT, "Inquiry from Red Carpet Homes App")
                                    putExtra(Intent.EXTRA_EMAIL, arrayOf("info@theredcarpethomes.co.ke"))
                                }
                                context.startActivity(Intent.createChooser(intent, "Send Email"))
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                        ) {
                            Text(
                                "Email: info@theredcarpethomes.co.ke",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/254706127197"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                        ) {
                            Text(
                                "Reach us: +254 706 127 197",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Social Media Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Follow Us On",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Facebook
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    val facebookUrl = "https://www.facebook.com/profile.php?id=61573893501823"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
                                    intent.setPackage("com.facebook.katana")
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        // Fallback to browser if app not installed
                                        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
                                        context.startActivity(fallbackIntent)
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color.Transparent)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_facebook),
                                        contentDescription = "Facebook",
                                        modifier = Modifier
                                            .size(32.dp)
                                            .align(Alignment.Center),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                Text(
                                    text = "Facebook",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // WhatsApp Channel
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://whatsapp.com/channel/0029VbBlx0s3bbVCzh0x6Y0b"))
                                    intent.setPackage("com.whatsapp")
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        // Fallback to browser if app not installed
                                        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://whatsapp.com/channel/0029VbBlx0s3bbVCzh0x6Y0b"))
                                        context.startActivity(fallbackIntent)
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color.Transparent)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.whatsapp_icon),
                                        contentDescription = "WhatsApp Channel",
                                        modifier = Modifier
                                            .size(32.dp)
                                            .align(Alignment.Center),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                Text(
                                    text = "WhatsApp Channel",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // TikTok
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    val tiktokUrl = "https://vm.tiktok.com/ZMHcX5uKUbnk4-K7Gxi/"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tiktokUrl))
                                    intent.setPackage("com.zhiliaoapp.musically")
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        // Fallback to browser if app not installed
                                        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(tiktokUrl))
                                        context.startActivity(fallbackIntent)
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color.Transparent)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_tiktok),
                                        contentDescription = "TikTok",
                                        modifier = Modifier
                                            .size(32.dp)
                                            .align(Alignment.Center),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                Text(
                                    text = "TikTok",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Instagram
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/the_redcarpethomes/"))
                                    intent.setPackage("com.instagram.android")
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        // Fallback to browser if app not installed
                                        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/the_redcarpethomes/"))
                                        context.startActivity(fallbackIntent)
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color.Transparent)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_instagram),
                                        contentDescription = "Instagram",
                                        modifier = Modifier
                                            .size(32.dp)
                                            .align(Alignment.Center),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                Text(
                                    text = "Instagram",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                // Back to Dashboard Button
                Button(
                    onClick = { navController.navigate(ROUT_DASHBOARD) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
                ) {
                    Text("Back to Dashboard", color = Color(0xFF8B0000), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }