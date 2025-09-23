package com.example.redcarpethomesassistant.ui.theme.screens.propertylist


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
import com.example.redcarpethomesassistant.navigation.ROUT_CONTACT

data class Property(
    val id: String,
    val title: String,
    val location: String,
    val size: String,
    val priceKes: String,
    val description: String,
    val amenities: List<String>,
    val imageResId: Int // Local drawable resource ID
)

@Composable
fun PropertyListScreen(navController: NavController) {
    val landProperties = listOf(
        Property(
            id = "kitengela",
            title = "Prime Plot in Kitengela",
            location = "Kitengela, Kajiado County",
            size = "1/8 Acre",
            priceKes = "Contact for price",
            description = "3Km from Oloika, 9Km from Namanga Road. Ideal for residential development.",
            amenities = listOf("Water on site", "Electricity in the neighborhood", "All weather road", "Schools nearby"),
            imageResId = R.drawable.kitengela // Local drawable
        ),
        Property(
            id = "juja",
            title = "Juja Farm Plot",
            location = "Juja Farm, Kiambu County",
            size = "1/8 Acre",
            priceKes = "Contact for price",
            description = "10-minute drive from Thika Super Highway, 40 minutes from Nairobi CBD.",
            amenities = listOf("Developed neighborhood", "Gated entrance & perimeter wall", "Tarmac road", "Schools nearby"),
            imageResId = R.drawable.juja // Local drawable
        ),
        Property(
            id = "watamu",
            title = "Beachfront Plot in Watamu",
            location = "Watamu, Kilifi County",
            size = "Not specified (Beachline)",
            priceKes = "Contact for price",
            description = "2.5 kms from the beachfront, 10 kms to Watamu town along the beachline.",
            amenities = listOf("Reliable water supply", "On-site power", "Social amenities", "Beach access"),
            imageResId = R.drawable.watamu // Local drawable
        ),
        Property(
            id = "diani",
            title = "Tiwi Beach Plot in Diani",
            location = "Diani, Kwale County",
            size = "50 x 100 ft",
            priceKes = "Contact for price",
            description = "1km from Diani Likoni Road, 1 km from Tiwi Beach.",
            amenities = listOf("Reliable water supply", "On-site power", "Compacted roads", "Social amenities"),
            imageResId = R.drawable.diani // Local drawable
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8B0000)) // Dark red
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with Home Icon and Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home Icon for Dashboard Navigation
                Icon(
                    painter = painterResource(id = R.drawable.home_icon), // Assuming home icon in drawable
                    contentDescription = "Go to Dashboard",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navController.navigate(ROUT_DASHBOARD) },
                    tint = Color(0xFFFFD700) // Goldish tint to match theme
                )
                Text(
                    text = "Available Lands for Sale",
                    fontSize = 24.sp,
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f) // Centers the title
                )
                Spacer(modifier = Modifier.width(40.dp)) // Placeholder to balance layout
            }
        }

        // Summarized Promotional Content (now scrollable)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Explore Prime Land Opportunities Across Kenya!",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "  We have premium land options tailored to your needs.",
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Locations",
                    fontSize = 16.sp,
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Kitengela, Juja Farm, Watamu, Diani, Riruta, Roysambu, Kiambu, Fedha",
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ready title deeds",
                    fontSize = 16.sp,
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "All land comes with genuine documentation",
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Flexible payment plans",
                    fontSize = 16.sp,
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "With affordable installments",
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Vertical Scrollable List of Land Properties
        items(landProperties) { property ->
            LandPropertyCard(property, navController)
        }
    }
}

@Composable
fun LandPropertyCard(property: Property, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp) // Maintained height
            .clickable { navController.navigate(ROUT_CONTACT) }, // Entire card navigable
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700)) // Goldish background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Image at the top (using local drawable)
            Image(
                painter = painterResource(id = property.imageResId),
                contentDescription = property.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = property.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B0000),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Location and Size
            Text(
                text = property.location,
                fontSize = 14.sp,
                color = Color(0xFF8B0000),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Size: ${property.size}",
                fontSize = 14.sp,
                color = Color(0xFF8B0000),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Contact for Price Button
            Button(
                onClick = { navController.navigate(ROUT_CONTACT) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B0000)), // Darker red box
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = property.priceKes,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Amenities as Plain Text
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                property.amenities.forEach { amenity ->
                    Text(
                        text = amenity,
                        fontSize = 12.sp,
                        color = Color(0xFF8B0000),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}