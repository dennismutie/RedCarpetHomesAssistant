// Updated NotificationScreen.kt - Fixed over-fetching; now only fetches if list empty on user change, with manual refresh for testing
package com.example.redcarpethomesassistant.ui.theme.screens.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.redcarpethomesassistant.data.AuthRepository
import com.example.redcarpethomesassistant.data.SharedReminders
import com.example.redcarpethomesassistant.navigation.ROUT_DASHBOARD
import android.widget.Toast
import kotlinx.coroutines.launch

// Simple data class for notifications
data class NotificationItem(
    val id: String,
    val title: String,
    val propertyTitle: String,
    val propertyType: String,
    val reminderTime: String,
    val reminderNote: String?
)

@Composable
fun NotificationScreen(navController: NavController) {
    val authRepo = remember { AuthRepository() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val userId by SharedReminders.userId.collectAsState(initial = null)

    // Fetch from shared list (reactive)
    val notifications by remember { derivedStateOf { SharedReminders.reminders } }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var notificationToDelete by remember { mutableStateOf<NotificationItem?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }

    // Auto-fetch ONLY if user changes AND list is empty (avoids overwriting local saves)
    LaunchedEffect(userId) {
        userId?.let { uid ->
            if (SharedReminders.reminders.isEmpty()) {
                try {
                    val reminders = authRepo.loadUserReminders(uid)
                    SharedReminders.setUser(uid, reminders)
                    if (reminders.isNotEmpty()) {
                        Toast.makeText(context, "Loaded ${reminders.size} reminders for your account", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "No previous reminders found for your account", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to load reminders: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFD700)), // Gold background like login
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Logo (matching login)
            Image(
                painter = painterResource(id = R.drawable.red_carpet_icon),
                contentDescription = "Red Carpet Homes Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp),
                contentScale = ContentScale.Fit
            )

            // Title (matching login)
            Text(
                text = "Your Reminders",
                color = Color(0xFF8B0000), // Deep red
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            Text(
                text = "Manage your saved property reminders here",
                color = Color(0xFF666666), // Gray
                fontSize = 16.sp
            )

            // Back to Dashboard Button (matching login)
            Button(
                onClick = { navController.navigate(ROUT_DASHBOARD) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)), // Deep red
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Back to Dashboard",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Notifications List Card (matching login card style)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x66000000)), // Semi-transparent gray
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Refresh Button at top for manual sync (useful for testing saves)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                if (isRefreshing) return@IconButton
                                isRefreshing = true
                                coroutineScope.launch {
                                    userId?.let { uid ->
                                        try {
                                            val reminders = authRepo.loadUserReminders(uid)
                                            SharedReminders.setUser(uid, reminders)
                                            Toast.makeText(context, "Refreshed: ${reminders.size} reminders", Toast.LENGTH_SHORT).show()
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Refresh failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                        } finally {
                                            isRefreshing = false
                                        }
                                    }
                                }
                            },
                            enabled = !isRefreshing
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh Reminders",
                                tint = Color.White
                            )
                        }
                    }

                    if (notifications.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "You do not currently have reminders.",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Your saved reminders will appear here. Try the refresh button if you just saved one.",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            itemsIndexed(notifications) { index, notification ->
                                NotificationItemCard(
                                    notification = notification,
                                    onDelete = {
                                        notificationToDelete = notification
                                        showDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Delete Confirmation Dialog - Now syncs to Firestore
            if (showDeleteDialog && notificationToDelete != null && userId != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                        notificationToDelete = null
                    },
                    title = {
                        Text(
                            text = "Delete Reminder?",
                            color = Color(0xFF8B0000),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            text = "Are you sure you want to delete the reminder for ${notificationToDelete!!.propertyTitle}? This action cannot be undone.",
                            color = Color(0xFF666666)
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    userId?.let { uid ->
                                        try {
                                            authRepo.deleteReminder(uid, notificationToDelete!!.id)
                                            SharedReminders.reminders.remove(notificationToDelete!!)
                                            Toast.makeText(context, "Reminder deleted from your account", Toast.LENGTH_SHORT).show()
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Failed to delete: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                showDeleteDialog = false
                                notificationToDelete = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Delete", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDeleteDialog = false
                            notificationToDelete = null
                        }) {
                            Text("Cancel", color = Color(0xFF8B0000))
                        }
                    }
                )
            } else if (showDeleteDialog && userId == null) {
                // Handle unauthenticated delete attempt
                Toast.makeText(context, "Please log in to manage reminders", Toast.LENGTH_SHORT).show()
                showDeleteDialog = false
            }
        }
    }
}

@Composable
fun NotificationItemCard(
    notification: NotificationItem,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B0000)
                )
                Text(
                    text = "${notification.propertyType}: ${notification.propertyTitle}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Time: ${notification.reminderTime}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                notification.reminderNote?.let { note ->
                    Text(
                        text = "Note: $note",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Reminder",
                    tint = Color(0xFF8B0000)
                )
            }
        }
    }
}