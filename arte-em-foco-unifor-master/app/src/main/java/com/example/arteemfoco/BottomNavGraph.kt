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
import com.example.arteemfoco.screens.admin.QuizAdminViewScreen
import com.example.arteemfoco.ui.theme.ArteEmFocoTheme

@Composable
//fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
//    NavHost(
//        navController = navController,
//        startDestination = BottomBarScreen.Quiz.route,
//        modifier = modifier
//    ) {
//        composable(route = BottomBarScreen.Quiz.route) {
//            QuizAdminScreen(navController)
//        }
//        composable(route = BottomBarScreen.Obras.route) {
//            ObraAdminScreen(navController)
//        }
//
//        composable(
//            route = "quizAddAdminScreen/{quizId}"
//        ) { backStackEntry ->
//            val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
//            QuizAddAdminScreen(navController, quizId)
//        }
//
//        composable(
//            route = "quizAddPerguntaAdminScreen/{quizId}"
//        ) { backStackEntry ->
//            val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
//            QuizAddPerguntaAdminScreen(navController, quizId)
//        }
//
//        composable("obraAddAdminScreen") { ObraAddAdminScreen(navController) }
//    }
//}

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

        composable("quizAddAdminScreen") {
            QuizAddAdminScreen(navController)
        }

        composable("quizAddPerguntaAdminScreen/{quizId}") { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId")
            if (quizId != null) {
                QuizAddPerguntaAdminScreen(navController, quizId)
            } else {
                // Exibir uma mensagem de erro ou retornar
            }
        }

        composable("quizAdminDetailsScreen/{quizId}") { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId")
            if (quizId != null) {
                QuizAdminViewScreen(navController, quizId)
            } else {
                // Exibir uma mensagem de erro ou retornar
            }
        }

        composable("obraAddAdminScreen") {
            ObraAddAdminScreen(navController)
        }
    }
}

// Codigo antigo!!!!!!!!!


//fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
//    NavHost(
//        navController = navController,
//        startDestination = BottomBarScreen.Quiz.route,
//        modifier = modifier
//    ) {
//        composable(route = BottomBarScreen.Quiz.route) {
//            QuizAdminScreen(navController)
//        }
//        composable(route = BottomBarScreen.Obras.route) {
//            ObraAdminScreen(navController)
//        }
//
//        composable("quizAddAdminScreen") {
//            QuizAddAdminScreen(navController)
//        }
//
//        composable("quizAddPerguntaAdminScreen/{quizId}") { backStackEntry ->
//            val quizId = backStackEntry.arguments?.getString("quizId") ?: "defaultQuizId"
//            QuizAddPerguntaAdminScreen(navController, quizId)
//        }
//
//        composable("quizAdminDetailsScreen/{quizId}") { backStackEntry ->
//            val quizId = backStackEntry.arguments?.getString("quizId") ?: "defaultQuizId"
//            QuizAdminViewScreen(navController, quizId)
//        }
//
//        composable("obraAddAdminScreen") {
//            ObraAddAdminScreen(navController)
//        }

//        composable(route = BottomBarScreen.Settings.route) {
//            SettingsScreen()
//        }
//    }
//}

//fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
//    NavHost(
//        navController = navController,
//        startDestination = BottomBarScreen.Quiz.route,
//        modifier = modifier
//    ) {
//        composable(route = BottomBarScreen.Quiz.route) {
//            QuizAdminScreen(navController)
//        }
//        composable(route = BottomBarScreen.Obras.route) {
//            ObraAdminScreen(navController)
//        }
//
//        composable("quizAddAdminScreen") { QuizAddAdminScreen(navController) }
//        composable("quizAddPerguntaAdminScreen/{quizId}") { backStackEntry ->
//            val quizId = backStackEntry.arguments?.getString("quizId") ?: "defaultQuizId"
//            QuizAddPerguntaAdminScreen(navController, quizId)
//        }
//        composable("obraAddAdminScreen") { ObraAddAdminScreen(navController) }
////        composable(route = BottomBarScreen.Settings.route) {
////            SettingsScreen()
////        }
//    }
//}