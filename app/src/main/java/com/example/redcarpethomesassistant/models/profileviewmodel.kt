//package com.example.redcarpethomesassistant.models
//
//
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.redcarpethomesassistant.data.SavedProperty // Your entity class (create if not exists)
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//
///**
// * ProfileViewModel handles user-specific data for the ProfileScreen, such as saved properties.
// * It uses Firebase Firestore to store/retrieve saved properties under the user's UID.
// * Assumes Firebase Auth is set up for user authentication.
// */
//class ProfileViewModel : ViewModel() {
//    private val auth = FirebaseAuth.getInstance()
//    private val db = FirebaseFirestore.getInstance()
//
//    // StateFlow for saved properties (reactive UI updates)
//    private val _savedProperties = MutableStateFlow<List<SavedProperty>>(emptyList())
//    val savedProperties: StateFlow<List<SavedProperty>> = _savedProperties.asStateFlow()
//
//    init {
//        loadSavedProperties()
//    }
//
//    /**
//     * Loads the user's saved properties from Firestore.
//     * Called on init and after save/delete.
//     */
//    private fun loadSavedProperties() {
//        val userId = auth.currentUser?.uid ?: return // Exit if no user logged in
//        viewModelScope.launch {
//            try {
//                val snapshot = db.collection("users")
//                    .document(userId)
//                    .collection("savedProperties")
//                    .get()
//                    .await()
//
//                val properties = snapshot.documents.mapNotNull { doc ->
//                    doc.toObject(SavedProperty::class.java)?.copy(id = doc.id) // Include document ID
//                }
//                _savedProperties.value = properties
//            } catch (e: Exception) {
//                // Handle error (e.g., log or show toast in UI)
//                e.printStackTrace()
//            }
//        }
//    }
//
//    /**
//     * Saves a property to the user's collection in Firestore.
//     */
//    fun saveProperty(property: SavedProperty) {
//        val userId = auth.currentUser?.uid ?: return // Exit if no user
//        viewModelScope.launch {
//            try {
//                db.collection("users")
//                    .document(userId)
//                    .collection("savedProperties")
//                    .document(property.id) // Use property ID as document ID
//                    .set(property)
//                    .await()
//                loadSavedProperties() // Refresh list
//            } catch (e: Exception) {
//                // Handle error
//                e.printStackTrace()
//            }
//        }
//    }
//
//    /**
//     * Deletes a saved property by ID from Firestore.
//     */
//    fun deleteProperty(propertyId: String) {
//        val userId = auth.currentUser?.uid ?: return // Exit if no user
//        viewModelScope.launch {
//            try {
//                db.collection("users")
//                    .document(userId)
//                    .collection("savedProperties")
//                    .document(propertyId)
//                    .delete()
//                    .await()
//                loadSavedProperties() // Refresh list
//            } catch (e: Exception) {
//                // Handle error
//                e.printStackTrace()
//            }
//        }
//    }
//
//    /**
//     * Saves a sample property for testing (call from UI button).
//     */
//    fun saveSampleProperty() {
//        val sample = SavedProperty(
//            id = "sample_${System.currentTimeMillis()}",
//            title = "Prime Plot in Kitengela",
//            location = "Kilimani, Nairobi",
//            price = 650000.0
//        )
//        saveProperty(sample)
//    }
//}