package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun QuizAdminViewScreen(navController: NavController, quizId: String) {
    var questions by remember { mutableStateOf(listOf<Question>()) }

    // Fetch perguntas do Firebase
    LaunchedEffect(quizId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("quizzes/$quizId/perguntas")
            .get()
            .addOnSuccessListener { result ->
                val questionsList = result.map { doc ->
                    Question(
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        alternatives = doc.get("alternatives") as? List<String> ?: emptyList(),
                        correctAnswerIndex = doc.getLong("correctAnswerIndex")?.toInt()
                    )
                }
                questions = questionsList
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 17.dp, top = 37.dp),
    ) {
        // Ícone de voltar no topo esquerdo
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Voltar",
            tint = Color.Black,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .clickable { navController.popBackStack() }
                .size(24.dp)
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            // Título
            Text(
                text = "Visualizar Quiz",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de perguntas
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(questions) { question ->
                    QuestionCard(question = question)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Botão "Adicionar Pergunta" fixado ao final
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("quizAdminScreen/$quizId") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar Pergunta")
            }
        }
    }
}

@Composable
fun QuestionCard(question: Question) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Título: ${question.title}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Descrição: ${question.description}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Alternativas:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            question.alternatives.forEachIndexed { index, alternative ->
                Text(
                    text = "- $alternative ${if (index == question.correctAnswerIndex) "(Correta)" else ""}",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuizAdminViewPreview() {
    val navController = rememberNavController()
    QuizAdminViewScreen(navController, "quiz123")
}
