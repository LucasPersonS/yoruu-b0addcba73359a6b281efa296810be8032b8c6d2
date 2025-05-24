package com.example.mentalhealth.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mentalhealth.ui.main.chatbot.ChatbotScreen
import com.example.mentalhealth.ui.main.mood.MoodTrackerScreen
import com.example.mentalhealth.ui.main.profile.ProfileScreen
import com.example.mentalhealth.ui.viewmodel.MentalHealthViewModel

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Chatbot : BottomNavItem("chatbot", Icons.Filled.Chat, "Chatbot")
    object Mood : BottomNavItem("mood", Icons.Filled.Mood, "Mood")
    object Profile : BottomNavItem("profile", Icons.Filled.Person, "Profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MentalHealthViewModel
) {
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    BottomNavItem.Chatbot,
                    BottomNavItem.Mood,
                    BottomNavItem.Profile
                )
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            mainNavController.navigate(item.route) {
                                popUpTo(mainNavController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = mainNavController,
            startDestination = BottomNavItem.Chatbot.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Chatbot.route) {
                ChatbotScreen(viewModel = viewModel)
            }
            composable(BottomNavItem.Mood.route) {
                MoodTrackerScreen(navController = mainNavController, viewModel = viewModel)
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(navController = mainNavController, viewModel = viewModel)
            }
        }
    }
}
