package com.example.arteemfoco.screens.quiz

import QuizViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.arteemfoco.CaixaTexto

@Composable
fun QuizEnterCodeScreen(navController: NavController, quizViewModel: QuizViewModel) {
    var code by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CaixaTexto(
            placeholder = "Código",
            text = code,
            onTextChange = { code = it }
        )

        Button(
            modifier = Modifier.width(250.dp),
            onClick = {
                if (code != "") {
                    quizViewModel.loadQuiz(code)
                    navController.navigate("quiz/$code")
                }

            }
        ) {
            Text("Jogar")
        }
    }
}


@Composable
@Preview
fun QuizEnterCodeScreenPreview() {
    val navController = rememberNavController()
//    QuizEnterCodeScreen(navController)
}
