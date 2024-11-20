// Screen.kt
package com.example.arteemfoco

sealed class Screen(val route: String) {
    object Start : Screen("startScreen")
    object Login : Screen("loginScreen")
    object Register : Screen("registerScreen")
    object QuizEnterCode : Screen("quizEnterCodeScreen")
    object QuizEnterName : Screen("quizEnterNameScreen")
    object Quiz : Screen("quizScreen")
    object QuizEnd : Screen("quizEndScreen")
    object Obra : Screen("obraScreen")
    object ObraView : Screen("obraViewScreen")

    // Telas Admin
    object ObraAdmin : Screen("obraAdminScreen")
    object QuizAdmin : Screen("quizAdminScreen")
    object QuizAddAdmin : Screen("quizAddAdminScreen")
    object QuizAddPerguntaAdmin : Screen("quizAddPerguntaAdminScreen")
    object ObraAddAdmin : Screen("obraAddAdminScreen")

    // Tela Principal (MainScreen) com BottomBar
    object Main : Screen("mainScreen")


}
