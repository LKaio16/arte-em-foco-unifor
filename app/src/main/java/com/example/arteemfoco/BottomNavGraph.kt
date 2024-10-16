package com.example.arteemfoco

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.arteemfoco.screens.ObraAdminScreen
import com.example.arteemfoco.screens.QuizAdminScreen
import com.example.arteemfoco.screens.SettingsScreen

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Quiz.route,
        modifier = modifier
    ) {
        composable(route = BottomBarScreen.Quiz.route) {
            QuizAdminScreen()
        }
        composable(route = BottomBarScreen.Obras.route) {
            ObraAdminScreen()
        }
//        composable(route = BottomBarScreen.Settings.route) {
//            SettingsScreen()
//        }
    }
}