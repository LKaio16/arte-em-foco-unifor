package com.example.arteemfoco.screens.quiz

import QuizViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizEnterCodeScreen(navController: NavController, quizViewModel: QuizViewModel) {
    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier
                .width(250.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),

        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Código") },
            modifier = Modifier
                .width(250.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),

        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (code.isNotBlank() && name.isNotBlank()) {
                    quizViewModel.loadQuiz(code)
                    navController.navigate("quiz/$code/$name")
                }
            },
            modifier = Modifier
                .width(250.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Jogar")
        }
    }
}

@Composable
@Preview
fun QuizEnterCodeScreenPreview() {
    val navController = rememberNavController()
    QuizEnterCodeScreen(navController, QuizViewModel())
}
