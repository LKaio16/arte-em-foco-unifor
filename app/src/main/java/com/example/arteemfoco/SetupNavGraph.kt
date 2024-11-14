// SetupNavGraph.kt
package com.example.arteemfoco

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.arteemfoco.screens.LoginScreen
import com.example.arteemfoco.screens.RegisterScreen
import com.example.arteemfoco.screens.StartScreen
import com.example.arteemfoco.screens.admin.ObraAddAdminScreen
import com.example.arteemfoco.screens.admin.ObraAdminScreen
import com.example.arteemfoco.screens.admin.QuizAddAdminScreen
import com.example.arteemfoco.screens.admin.QuizAddPerguntaAdminScreen
import com.example.arteemfoco.screens.obras.ObraScreen
import com.example.arteemfoco.screens.obras.ObraViewScreen
import com.example.arteemfoco.screens.quiz.QuizEndScreen
import com.example.arteemfoco.screens.quiz.QuizEnterCodeScreen
import com.example.arteemfoco.screens.quiz.QuizEnterNameScreen
import com.example.arteemfoco.screens.quiz.QuizScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Start.route) {
        composable(Screen.Start.route) { StartScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.QuizEnterCode.route) { QuizEnterCodeScreen(navController) }
        composable(Screen.QuizEnterName.route) { QuizEnterNameScreen(navController) }
        composable(Screen.Quiz.route) { QuizScreen(navController) }
        composable(Screen.QuizEnd.route) { QuizEndScreen(navController) }
        composable(Screen.Obra.route) { ObraScreen(navController) }
        composable(Screen.ObraView.route) { ObraViewScreen(navController) }

        // Telas Admin
        composable(Screen.ObraAdmin.route) { ObraAdminScreen(navController) }
        composable(Screen.QuizAddAdmin.route) { QuizAddAdminScreen(navController) }
        composable(Screen.QuizAddPerguntaAdmin.route) { QuizAddPerguntaAdminScreen(navController) }
        composable(Screen.ObraAddAdmin.route) { ObraAddAdminScreen(navController) }

        // Navegação para a MainScreen (com BottomBar)
        composable(Screen.Main.route) { MainScreen() }
    }
}
