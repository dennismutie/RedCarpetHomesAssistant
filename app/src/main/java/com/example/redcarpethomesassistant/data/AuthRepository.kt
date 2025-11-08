// Updated file: data/AuthRepository.kt - Reverted to default (1-hour expiration); added logging for debugging
package com.example.redcarpethomesassistant.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.redcarpethomesassistant.ui.theme.screens.notifications.NotificationItem
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun loadUserReminders(userId: String): List<NotificationItem> {
        return try {
            val snapshot = db.collection("users").document(userId).collection("reminders").get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(NotificationItem::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun saveReminder(userId: String, reminder: NotificationItem) {
        db.collection("users").document(userId).collection("reminders").document(reminder.id).set(reminder).await()
    }

    suspend fun deleteReminder(userId: String, reminderId: String) {
        db.collection("users").document(userId).collection("reminders").document(reminderId).delete().await()
    }

    suspend fun sendPasswordReset(email: String): Result<Unit> {
        return try {
            Log.d("AuthRepository", "Sending password reset to $email") // Debug log
            auth.sendPasswordResetEmail(email).await()
            Log.d("AuthRepository", "Password reset email sent successfully to $email") // Debug log
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error sending password reset to $email: ${e.message}", e) // Error log
            Result.failure(e)
        }
    }
}