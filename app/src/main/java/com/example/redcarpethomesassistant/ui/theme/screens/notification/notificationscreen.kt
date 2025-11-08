// Updated NotificationScreen.kt - Reverted to conditional load (only if empty) to preserve local saves; always loads on login via AuthRepo
package com.example.redcarpethomesassistant.ui.theme.screens.notifications

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    // Direct use of SnapshotStateList (observable, no derivedStateOf needed)
    val notifications = SharedReminders.reminders
    var showDeleteDialog by remember { mutableStateOf(false) }
    var notificationToDelete by remember { mutableStateOf<NotificationItem?>(null) }

    // Fixed: Load ONLY if list is empty on userId change (preserves local saves; login already loads from server)
    LaunchedEffect(userId) {
        userId?.let { uid ->
            if (SharedReminders.reminders.isEmpty()) {
                try {
                    Log.d("NotificationScreen", "Loading reminders for user: $uid (list was empty)") // Debug log
                    val reminders = authRepo.loadUserReminders(uid)
                    SharedReminders.setUser(uid, reminders)
                    if (reminders.isNotEmpty()) {
                        Toast.makeText(context, "Loaded ${reminders.size} reminders for your account", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "No previous reminders found for your account", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("NotificationScreen", "Load failed for user $uid: ${e.message}", e)
                    Toast.makeText(context, "Failed to load reminders: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("NotificationScreen", "Skipping load for user: $uid (local list has ${SharedReminders.reminders.size} items)") // Debug log
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50)), // Green background (Material Green)
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Reduced from 24.dp for tighter overall spacing
        ) {
            // Logo (matching login, but with reduced bottom padding for better fit and less gap)
            Image(
                painter = painterResource(id = R.drawable.red_carpet_icon),
                contentDescription = "Red Carpet Homes Logo",
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp), // Removed bottom padding; rely on spacedBy for gaps
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

            // Notifications List Card (matching login card style) - Moved up, Back button now below
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
                    // Removed refresh button - auto-sync only on load

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
                                    text = "Your saved reminders will appear here.",
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
                            items(
                                items = notifications,
                                key = { notification -> notification.id } // Stable key by ID
                            ) { notification ->
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

            // Back to Dashboard Button (matching login) - Moved below Card for better flow
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
                                // Fixed: Launch coroutine but close dialog INSIDE after operation (syncs timing)
                                coroutineScope.launch {
                                    userId?.let { uid ->
                                        try {
                                            Log.d("NotificationScreen", "Attempting to delete reminder ID: ${notificationToDelete!!.id} for user: $uid") // Debug log
                                            authRepo.deleteReminder(uid, notificationToDelete!!.id)
                                            SharedReminders.removeReminder(notificationToDelete!!.id)
                                            Toast.makeText(context, "Reminder deleted from your account", Toast.LENGTH_SHORT).show()
                                        } catch (e: Exception) {
                                            Log.e("NotificationScreen", "Delete failed for ID ${notificationToDelete!!.id}: ${e::class.simpleName} - ${e.message}", e) // Detailed log
                                            val errorMsg = "${e::class.simpleName}: ${e.message ?: "Unknown error (check logs)"}"
                                            Toast.makeText(context, "Failed to delete: $errorMsg", Toast.LENGTH_LONG).show()
                                        } finally {
                                            // Close dialog AFTER operation completes (prevents recomposition desync)
                                            showDeleteDialog = false
                                            notificationToDelete = null
                                        }
                                    } ?: run {
                                        Toast.makeText(context, "User ID not available - cannot delete", Toast.LENGTH_SHORT).show()
                                        showDeleteDialog = false
                                        notificationToDelete = null
                                    }
                                }
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