// SetupNavGraph.kt
package com.example.arteemfoco

import QuizViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.arteemfoco.screens.LoginScreen
import com.example.arteemfoco.screens.RegisterScreen
import com.example.arteemfoco.screens.StartScreen
import com.example.arteemfoco.screens.admin.ObraAddAdminScreen
import com.example.arteemfoco.screens.admin.ObraAdminScreen
import com.example.arteemfoco.screens.admin.ObraAdminViewScreen
import com.example.arteemfoco.screens.admin.QuizAdminScreen
import com.example.arteemfoco.screens.admin.QuizAddAdminScreen
import com.example.arteemfoco.screens.admin.QuizAddPerguntaAdminScreen
import com.example.arteemfoco.screens.admin.QuizAdminViewScreen
import com.example.arteemfoco.screens.admin.RankingScreen
import com.example.arteemfoco.screens.obras.ObraScreen
import com.example.arteemfoco.screens.obras.ObraViewScreen
import com.example.arteemfoco.screens.quiz.QuizEndScreen
import com.example.arteemfoco.screens.quiz.QuizEnterCodeScreen
import com.example.arteemfoco.screens.quiz.QuizScreen


@Composable
fun SetupAdminNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val quizViewModel: QuizViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.QuizAdmin.route) {
        composable(Screen.Start.route) { StartScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.QuizEnterCode.route) {
            QuizEnterCodeScreen(
                navController = navController, quizViewModel = quizViewModel)
        }

        composable(Screen.Obra.route) { ObraScreen(navController) }

        // Telas Admin
        composable(Screen.ObraAdmin.route) { ObraAdminScreen(navController) }
        composable(Screen.ObraAddAdmin.route) { ObraAddAdminScreen(navController) }


        composable(Screen.QuizAdmin.route) { QuizAdminScreen(navController) }
        composable(Screen.QuizAddAdmin.route) { QuizAddAdminScreen(navController) }

        composable(
            route = "quizAdminScreen/{quizId}",
            arguments = listOf(navArgument("quizId") { type = NavType.StringType })
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId")
            if (quizId != null) {
                QuizAddPerguntaAdminScreen(navController = navController, quizId = quizId)
            }
        }

        composable(
            route = "obraAdminView/{obraId}",
            arguments = listOf(navArgument("obraId") { type = NavType.StringType })
        ) { backStackEntry ->
            val obraId = backStackEntry.arguments?.getString("obraId")
            if (obraId != null) {
                ObraAdminViewScreen(navController = navController, obraId = obraId)
            }
        }

        composable(
            route = "rankingScreen/{quizId}",
            arguments = listOf(navArgument("quizId") { type = NavType.StringType })
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId")
            if (quizId != null) {
                RankingScreen(navController = navController, quizId = quizId)
            }
        }


        composable(
            route = "obraView/{obraId}",
            arguments = listOf(navArgument("obraId") { type = NavType.StringType })
        ) { backStackEntry ->
            val obraId = backStackEntry.arguments?.getString("obraId")
            if (obraId != null) {
                ObraViewScreen(navController = navController, obraId = obraId)
            }
        }


        composable(
            route = "quizViewScreen/{quizId}",
            arguments = listOf(navArgument("quizId") { type = NavType.StringType })
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId")
            if (quizId != null) {
                QuizAdminViewScreen(navController = navController, quizId = quizId)
            }
        }


        // Navegação para a MainScreen (com BottomBar)
        composable(Screen.Main.route) { MainScreen() }

    }
}
