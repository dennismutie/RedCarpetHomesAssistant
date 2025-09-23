package com.example.redcarpethomesassistant.ui.theme.screens.home


import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.redcarpethomesassistant.R
import com.example.redcarpethomesassistant.navigation.ROUT_CONTACT
import com.example.redcarpethomesassistant.navigation.ROUT_DASHBOARD

data class Apartment(
    val id: String,
    val title: String,
    val location: String,
    val size: String,
    val priceKes: Double,
    val description: String,
    val amenities: List<String>,
    val imageUrl: String
)

@Composable
fun HomeScreen(navController: NavController) {
    // Email launcher for Gmail/Email app
    val emailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }

    // Rental Apartments (Ready Finished, Inspired by Website)
    val apartments = listOf(
        Apartment(
            id = "kilimani",
            title = "Luxury 2-Bedroom in Kilimani",
            location = "Kilimani, Nairobi",
            size = "100 sqm",
            priceKes = 80000.0, // Monthly rental
            description = "Modern apartment with spacious layouts and high-end appliances in a prime location.",
            amenities = listOf("Furnished/Unfurnished", "Gym & Pool", "24/7 Security", "Near CBD"),
            imageUrl = "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80"
        ),
        Apartment(
            id = "lavington",
            title = "3-Bedroom House in Lavington",
            location = "Lavington, Nairobi",
            size = "150 sqm",
            priceKes = 120000.0, // Monthly
            description = "Spacious, finished house with 7+ projects in the area, high ROI with 18% return.",
            amenities = listOf("Garden", "Parking", "Security", "Near Schools"),
            imageUrl = "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80"
        ),
        Apartment(
            id = "westlands",
            title = "1-Bedroom Apartment in Westlands",
            location = "Westlands, Nairobi",
            size = "80 sqm",
            priceKes = 60000.0, // Monthly
            description = "Handpicked apartment in high-demand area with flexible financing options.",
            amenities = listOf("Modern Finishes", "Balcony", "Near Shopping", "Expert Guidance"),
            imageUrl = "https://images.unsplash.com/photo-1570129477492-45c003edd2be?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80"
        ),
        Apartment(
            id = "runda",
            title = "4-Bedroom Villa in Runda",
            location = "Runda, Nairobi",
            size = "200 sqm",
            priceKes = 150000.0, // Monthly
            description = "Exclusive villa with spacious layouts, 20% average price growth over 3 years.",
            amenities = listOf("High-End Appliances", "Gated Community", "Near Hospitals", "Petrol Station Nearby"),
            imageUrl = "https://images.unsplash.com/photo-1571896349842-33c89424de2d?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8B0000)) // Dark red
    ) {
        // Reduced Size Red Carpet Icon as Background
        Image(
            painter = painterResource(id = R.drawable.red_carpet_icon),
            contentDescription = "Red Carpet Background Icon",
            modifier = Modifier
                .fillMaxHeight(0.5f) // Reduced to 50% of screen height
                .fillMaxWidth()
                .align(Alignment.Center),
            contentScale = ContentScale.Fit // Fits within the reduced size
        )

        // Header with Red Carpet Icon at Top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate(ROUT_DASHBOARD) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
            ) {
                Text("Back to Dashboard", color = Color(0xFF8B0000), fontSize = 14.sp)
            }
            Image(
                painter = painterResource(id = R.drawable.red_carpet_icon),
                contentDescription = "Red Carpet Homes Icon",
                modifier = Modifier
                    .size(80.dp) // Smaller size for top icon
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
        }

        // Content Card (Expanded to fit left/right fully and extend down more)
        Card(
            modifier = Modifier
                .fillMaxWidth() // Full width for left/right fit
                .fillMaxHeight(0.95f) // Expanded height to fit down more (95% of screen)
                .padding(top = 80.dp, start = 0.dp, end = 0.dp, bottom = 0.dp), // Removed side/bottom padding for full fit, keep top for header
            colors = CardDefaults.cardColors(containerColor = Color(0x66000000)), // Semi-transparent dark gray
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Enable scrolling
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Promotional Intro Card (from Discover... up to Stats) - Tightened layout
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp), // Reduced bottom padding
                    colors = CardDefaults.cardColors(containerColor = Color(0x40FFD700)), // Semi-transparent gold for luxury fit
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp), // Reduced internal padding
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Promotional Text from Website - Updated to match exactly
                        Text(
                            text = "Discover Exclusive Homes in Nairobi’s Prime Locations!",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Here’s a sneak peek at just a few of our carefully selected properties in high-demand areas. Whether you’re searching for your dream home or a smart investment, we have premium real estate options tailored to your needs.",
                            fontSize = 14.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 12.dp) // Reduced padding
                        )
                        Text(
                            text = "Browse below for a glimpse of what’s available—there’s much more waiting for you!",
                            fontSize = 14.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp) // Reduced padding
                        )

                        // Stats from Website - Updated labels to match exactly
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 0.dp), // Removed bottom padding to eliminate gap
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatCard("7+", "Successfully Finished Projects", "Both In Kilimani & Lavington Areas")
                            StatCard("18+", "Years Of Being In The Market", "")
                            StatCard("18%", "Return On Investment", "")
                            StatCard("20%", "Average price growth over the last 3 years", "")
                        }
                    }
                }

                // Features Checkboxes
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CheckboxWithText("Prime Locations: Westlands, Kilimani, Kileleshwa, Lavington, Runda")
                    CheckboxWithText("Luxury Living: Modern finishes, spacious layouts, high-end appliances")
                    CheckboxWithText("Furnished & Unfurnished Options to match your preferences")
                    CheckboxWithText("Flexible Payment Plans & Mortgage Assistance")
                }

                // Nearby Places Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Hospital",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "School",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Restaurant",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                // Call to Action
                Text(
                    text = "Interested in any of the above prime properties? Let’s talk!",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { navController.navigate(ROUT_CONTACT) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)) // WhatsApp green
                ) {
                    Text("Chat with us on WhatsApp", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                // Email Button
                Button(
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:rachael@theredcarpethomes.co.ke")
                        }
                        emailLauncher.launch(Intent.createChooser(emailIntent, "Send email"))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp) // Increased height to prevent text clipping
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)) // Dark red to match theme
                ) {
                    Text("Email rachael@theredcarpethomes.co.ke", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                // Apartment Listings
                Text(
                    text = "Featured Apartments",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(apartments) { apartment ->
                        ApartmentCard(apartment, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(number: String, label: String, subtitle: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = number,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        if (subtitle.isNotEmpty()) {
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CheckboxWithText(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = true,
            onCheckedChange = { },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.White
            )
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun ApartmentCard(apartment: Apartment, navController: NavController) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(700.dp) // Uniform height for all cards, tall enough for full content
            .clickable { navController.navigate(ROUT_CONTACT) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween // Distributes content evenly
        ) {
            // Property Image
            AsyncImage(
                model = apartment.imageUrl,
                contentDescription = apartment.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.red_carpet_icon), // Fallback to icon
                error = painterResource(R.drawable.red_carpet_icon)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column { // Wrap details in Column for top alignment
                Text(
                    text = apartment.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B0000)
                )
                Text(
                    text = apartment.location,
                    fontSize = 14.sp,
                    color = Color(0xFF8B0000)
                )
                Text(
                    text = "Size: ${apartment.size}",
                    fontSize = 12.sp,
                    color = Color(0xFF8B0000)
                )
                Text(
                    text = "KES ${String.format("%,.0f", apartment.priceKes)}/month",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B0000)
                )
                Text(
                    text = apartment.description,
                    fontSize = 12.sp,
                    color = Color(0xFF8B0000)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(apartment.amenities) { amenity ->
                        AssistChip(
                            onClick = {},
                            label = { Text(amenity, fontSize = 10.sp) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(0xFF8B0000),
                                labelColor = Color.White
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Per-Apartment CTA - Now at bottom, always visible
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Interested in this property? Let’s Talk!",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B0000),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Want to learn more about this exclusive home or need full pricing & ownership details? Thinking about scheduling a viewing to explore it in person?",
                    fontSize = 12.sp,
                    color = Color(0xFF8B0000),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = "Chat with us instantly on WhatsApp to connect with an expert agent!",
                        fontSize = 12.sp,
                        color = Color(0xFF8B0000),
                        textAlign = TextAlign.Center
                    )
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Unspecified
                    )
                }
                Button(
                    onClick = { navController.navigate(ROUT_CONTACT) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                ) {
                    Text("Connect Now", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Text(
                    text = "Prefer email? Reach us at rachael@theredcarpethomes.co.ke for more details.",
                    fontSize = 10.sp,
                    color = Color(0xFF8B0000),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}