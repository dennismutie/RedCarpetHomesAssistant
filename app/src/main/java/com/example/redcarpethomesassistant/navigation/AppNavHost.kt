package com.example.redcarpethomesassistant.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.redcarpethomesassistant.screens.*
import com.example.redcarpethomesassistant.ui.theme.screens.contacts.ContactScreen
import com.example.redcarpethomesassistant.ui.theme.screens.dashboard.DashboardScreen
import com.example.redcarpethomesassistant.ui.theme.screens.home.HomeScreen
import com.example.redcarpethomesassistant.ui.theme.screens.login.LoginScreen
import com.example.redcarpethomesassistant.ui.theme.screens.profile.ProfileScreen
import com.example.redcarpethomesassistant.ui.theme.screens.propertylist.PropertyListScreen
import com.example.redcarpethomesassistant.ui.theme.screens.register.RegisterScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(), // Line 23
    startDestination: String = ROUT_DASHBOARD
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
//        composable(ROUT_SPLASH) { SplashScreen(navController) }
        composable(ROUT_LOGIN) { LoginScreen(navController) }
        composable(ROUT_REGISTER) { RegisterScreen(navController) }
        composable(ROUT_DASHBOARD) { DashboardScreen(navController) }
        composable(ROUT_HOME) { HomeScreen(navController) }
        composable(ROUT_PROFILE) { ProfileScreen(navController) }
        composable(ROUT_PROPERTYLIST) { PropertyListScreen(navController) }
        composable(ROUT_CONTACT) { ContactScreen(navController) }
    }
}