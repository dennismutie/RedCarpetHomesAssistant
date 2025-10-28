// New file: data/AuthRepository.kt - Handles Firebase Auth and Firestore for user-specific reminders
package com.example.redcarpethomesassistant.data

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
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}