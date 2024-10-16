package com.example.arteemfoco

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.arteemfoco.screens.admin.ObraAddAdminScreen
import com.example.arteemfoco.screens.admin.ObraAdminScreen
import com.example.arteemfoco.screens.admin.QuizAddAdminScreen
import com.example.arteemfoco.screens.admin.QuizAddPerguntaAdminScreen
import com.example.arteemfoco.screens.admin.QuizAdminScreen
import com.example.arteemfoco.ui.theme.ArteEmFocoTheme

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Quiz.route,
        modifier = modifier
    ) {
        composable(route = BottomBarScreen.Quiz.route) {
            QuizAdminScreen(navController)
        }
        composable(route = BottomBarScreen.Obras.route) {
            ObraAdminScreen(navController)
        }

        composable("quizAddAdminScreen") { QuizAddAdminScreen(navController) }
        composable("quizAddPerguntaAdminScreen") { QuizAddPerguntaAdminScreen(navController) }
        composable("obraAddAdminScreen") { ObraAddAdminScreen(navController) }
//        composable(route = BottomBarScreen.Settings.route) {
//            SettingsScreen()
//        }
    }
}