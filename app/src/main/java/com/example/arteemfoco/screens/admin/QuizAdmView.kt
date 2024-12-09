package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Ícone de voltar
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Voltar",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = 17.dp, top = 40.dp)
                .clickable { navController.popBackStack() }
                .size(30.dp)
        )

        // Título
        Text(
            text = "Visualizar Quiz",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
        )

        // Lista de perguntas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 80.dp, bottom = 180.dp) // Ajuste o espaço entre o título e as perguntas
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(questions) { question ->
                    QuestionCard(question = question)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }


        // Box com código e botões no final
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
                .align(Alignment.BottomCenter) // Posiciona no fundo da tela
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Código do Quiz
                Text(
                    text = "Código do Quiz: ${quizId.take(4)}",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Botões
                Button(
                    onClick = { navController.navigate("rankingScreen/$quizId") },
                    modifier = Modifier.width(200.dp),
                ) {
                    Text(
                        text = "Ver Ranking",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { navController.navigate("quizAdminScreen/$quizId") },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "Adicionar Pergunta",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}


@Composable
fun QuestionCard(question: Question) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Título: ${question.title}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Descrição: ${question.description}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Alternativas:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            question.alternatives.forEachIndexed { index, alternative ->
                Text(
                    text = "- $alternative ${if (index == question.correctAnswerIndex) "(Correta)" else ""}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
