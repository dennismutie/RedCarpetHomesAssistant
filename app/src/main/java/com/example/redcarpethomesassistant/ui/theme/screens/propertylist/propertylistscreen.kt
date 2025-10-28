// Updated PropertyListScreen.kt - Reverted to auth-based single-note save (no email prompt)
package com.example.redcarpethomesassistant.ui.theme.screens.propertylist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import androidx.navigation.NavController
import com.example.redcarpethomesassistant.R
import com.example.redcarpethomesassistant.data.AuthRepository
import com.example.redcarpethomesassistant.data.SharedReminders
import com.example.redcarpethomesassistant.navigation.ROUT_DASHBOARD
import com.example.redcarpethomesassistant.navigation.ROUT_CONTACT
import com.example.redcarpethomesassistant.ui.theme.screens.notifications.NotificationItem
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.widget.Toast
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PropertyListScreen(navController: NavController) {
    val authRepo = remember { AuthRepository() }
    val coroutineScope = rememberCoroutineScope()
    val userId by SharedReminders.userId.collectAsState(initial = null)
    val context = LocalContext.current

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
        // Red Carpet Homes Icon
        item {
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
        }

        // Header with Back Arrow and Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Arrow for Dashboard Navigation
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Using Material Icon as fallback
                    contentDescription = "Back to Dashboard",
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, shape = RoundedCornerShape(4.dp)) // White background for contrast
                        .padding(4.dp)
                        .clickable { navController.navigate(ROUT_DASHBOARD) },
                    tint = Color(0xFF8B0000) // Darker red for better contrast
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
                    text = "We have premium land options tailored to your needs.",
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
            LandPropertyCard(
                property = property,
                navController = navController,
                sharedReminders = SharedReminders.reminders,
                authRepo = authRepo,
                userId = userId,
                coroutineScope = coroutineScope,
                context = context
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LandPropertyCard(
    property: Property,
    navController: NavController,
    sharedReminders: SnapshotStateList<NotificationItem>,
    authRepo: AuthRepository,
    userId: String?,
    coroutineScope: CoroutineScope,
    context: android.content.Context
) {
    var showDialog by remember { mutableStateOf(false) }
    var reminderNote by remember { mutableStateOf("") }
    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    val isReminded = sharedReminders.any { it.id == property.id }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp) // Further increased height to ensure no text is cut out
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

            // Set Reminder to Purchase Button - Added below Contact for Price
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (userId == null) {
                        Toast.makeText(context, "Please log in to set reminders", Toast.LENGTH_SHORT).show()
                        // TODO: Navigate to login if needed
                        return@Button
                    }
                    if (isReminded) {
                        // Cancel reminder - Delete from Firestore and local
                        coroutineScope.launch {
                            try {
                                authRepo.deleteReminder(userId, property.id)
                                sharedReminders.removeAll { it.id == property.id }
                                Toast.makeText(context, "Reminder deleted", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Failed to delete reminder: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // Show dialog to type note
                        showDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isReminded) Color.Green else Color(0xFF8B0000)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = if (isReminded) "Reminder Set" else "Set Reminder",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isReminded) "Reminder Active" else "Set Reminder to Purchase",
                    fontSize = 14.sp,
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

    // Dialog for typing reminder note
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Set Reminder Note") },
            text = {
                Column {
                    Text("Type a note for your reminder:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reminderNote,
                        onValueChange = { reminderNote = it },
                        label = { Text("Reminder Note") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (reminderNote.isNotBlank() && userId != null) {
                            val newItem = NotificationItem(
                                id = property.id, // Use property ID as reminder ID
                                title = "Reminder for ${property.title}",
                                propertyTitle = property.title,
                                propertyType = "Land",
                                reminderTime = currentTime,
                                reminderNote = reminderNote
                            )
                            coroutineScope.launch {
                                try {
                                    authRepo.saveReminder(userId, newItem)
                                    sharedReminders.add(newItem)
                                    Toast.makeText(context, "Reminder saved", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Failed to save reminder: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (userId == null) {
                            Toast.makeText(context, "Please log in to set reminders", Toast.LENGTH_SHORT).show()
                        }
                        showDialog = false
                        reminderNote = ""
                    }
                ) {
                    Text("Set Reminder")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}