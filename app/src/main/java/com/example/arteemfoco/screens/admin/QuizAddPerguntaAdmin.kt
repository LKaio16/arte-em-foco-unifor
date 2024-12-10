package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

data class Question(
    val title: String = "",
    val description: String = "",
    val alternatives: List<String> = emptyList(),
    val correctAnswerIndex: Int? = null
)

@Composable
fun QuizAddPerguntaAdminScreen(navController: NavController, quizId: String) {
    var questionTitle by remember { mutableStateOf("") }
    var questionDescription by remember { mutableStateOf("") }
    var alternatives by remember { mutableStateOf(listOf<String>()) }
    var alternativeText by remember { mutableStateOf("") }
    var correctAnswerIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Topo com botão de voltar e título
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 17.dp, top = 40.dp)
                    .clickable { navController.popBackStack() }
                    .size(30.dp)
            )

            Text(
                text = "Adicionar Pergunta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Conteúdo rolável
        Column(
            modifier = Modifier
                .weight(1f) // Ocupa o espaço restante
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // Título da pergunta
            Text(
                text = "Título da Pergunta",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = questionTitle,
                onValueChange = { questionTitle = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite o título da pergunta") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descrição da pergunta
            Text(
                text = "Descrição da Pergunta",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = questionDescription,
                onValueChange = { questionDescription = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite a descrição da pergunta") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Alternativas
            Text(
                text = "Alternativas",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = alternativeText,
                onValueChange = { alternativeText = it },
                placeholder = { Text("Digite uma alternativa") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    if (alternativeText.isNotEmpty()) {
                        alternatives = alternatives + alternativeText
                        alternativeText = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Adicionar Alternativa")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de alternativas
            alternatives.forEachIndexed { index, alternative ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = correctAnswerIndex == index,
                        onClick = { correctAnswerIndex = index }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = alternative,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Botão de "Salvar Pergunta" fixo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    val db = FirebaseFirestore.getInstance()

                    val questionData = mapOf(
                        "title" to questionTitle,
                        "description" to questionDescription,
                        "alternatives" to alternatives,
                        "correctAnswerIndex" to correctAnswerIndex
                    )

                    db.collection("quizzes/$quizId/perguntas")
                        .add(questionData)
                        .addOnSuccessListener {
                            navController.popBackStack()
                        }
                        .addOnFailureListener {
                            // Tratamento de erro
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Pergunta", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuizAddPerguntaAdminScreenPreview() {
    val navController = rememberNavController()
    QuizAddPerguntaAdminScreen(navController, "quiz123")
}
