package com.example.redcarpethomesassistant.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.redcarpethomesassistant.ui.theme.screens.notifications.NotificationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SharedReminders {
    private val _reminders = mutableStateListOf<NotificationItem>()
    val reminders: SnapshotStateList<NotificationItem> = _reminders

    private val _userId = MutableStateFlow<String?>(null)
    val userId = _userId.asStateFlow()

    // Call after login/register to load user-specific reminders
    suspend fun setUser(userId: String, remindersList: List<NotificationItem>) {
        _userId.value = userId
        _reminders.clear()
        _reminders.addAll(remindersList)
    }

    // Clear on logout
    fun clear() {
        _reminders.clear()
        _userId.value = null
    }

    // Internal helpers (called by screens via repo)
    fun addReminder(reminder: NotificationItem) {
        _reminders.add(reminder)
    }

    fun removeReminder(reminderId: String) {
        _reminders.removeAll { it.id == reminderId }
    }
}