package com.example.redcarpethomesassistant.models

//package com.example.redcarpethomesassistant.ui.theme.screens.profile

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

data class User(val name: String, val email: String, val password: String)

class ProfileViewModel : ViewModel() {
    var currentUser by mutableStateOf<User?>(null)
    var isRegistering by mutableStateOf(false)
    var isEditing by mutableStateOf(false)
    var isLoggingIn by mutableStateOf(false)

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    private val _registeredUsers = mutableStateMapOf<String, User>()
    val registeredUsers: Map<String, User> get() = _registeredUsers

    val savedProperties = listOf(
        "Prime Plot in Kitengela",
        "Juja Farm Plot"
    )

    fun createAccount() {
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            if (_registeredUsers.containsKey(email)) {
                errorMessage = "User already exists!"
                return
            }
            val newUser = User(name, email, password)
            _registeredUsers[email] = newUser
            currentUser = newUser
            resetForm()
        } else {
            errorMessage = "All fields are required"
        }
    }

    fun login() {
        val user = _registeredUsers[email]
        if (user != null && user.password == password) {
            currentUser = user
            resetForm()
        } else {
            errorMessage = "Invalid credentials"
        }
    }

    fun logout() {
        currentUser = null
        resetForm()
    }

    fun saveChanges() {
        currentUser = currentUser?.copy(name = name, email = email)
        currentUser?.let {
            _registeredUsers[it.email] = it
        }
        isEditing = false
    }

    fun resetForm() {
        name = ""
        email = ""
        password = ""
        isRegistering = false
        isLoggingIn = false
        isEditing = false
        errorMessage = null
    }

    fun loadCurrentUserData() {
        name = currentUser?.name.orEmpty()
        email = currentUser?.email.orEmpty()
    }
}
