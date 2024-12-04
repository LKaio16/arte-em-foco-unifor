package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore

//Preciso colocar o "Salvar Pergunta" em outra box, diferente das alternativas.

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // voltar
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Voltar",
            tint = Color.Black,
            modifier = Modifier
                .padding(start = 17.dp, top = 37.dp)
                .clickable { navController.popBackStack() }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .verticalScroll(rememberScrollState()) // Permite rolar o conteúdo
                .padding(horizontal = 16.dp)
        ) {
            // Título da pergunta
            Text(text = "Título da Pergunta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = questionTitle,
                onValueChange = { questionTitle = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite o título da pergunta") }
            )

            Spacer(Modifier.height(16.dp))

            // Descrição da pergunta
            Text(text = "Descrição da Pergunta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = questionDescription,
                onValueChange = { questionDescription = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite a descrição da pergunta") }
            )

            Spacer(Modifier.height(16.dp))

            // Alternativas
            Text(text = "Alternativas", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = alternativeText,
                onValueChange = { alternativeText = it },
                label = { Text("Nova alternativa") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Button(onClick = {
                if (alternativeText.isNotEmpty()) {
                    alternatives = alternatives + alternativeText
                    alternativeText = ""
                }
            }) {
                Text("Adicionar Alternativa")
            }

            Spacer(Modifier.height(16.dp))

            // Lista de alternativas
            alternatives.forEachIndexed { index, alternative ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = correctAnswerIndex == index,
                        onClick = { correctAnswerIndex = index }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = alternative, fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(16.dp))
        }

        // Botão de "Salvar Pergunta" fixado na parte inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
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
                Text("Salvar Pergunta")
            }
        }
    }
}


@Composable
@Preview
fun QuizAddPerguntaAdminScreenPreview() {
    val navController = rememberNavController()
    QuizAddPerguntaAdminScreen(navController, "quiz123")
}


@Preview
@Composable
private fun QuizAddPerguntaAdminPreview() {
    val navController = rememberNavController()
    // Passar um valor de teste para o quizId
    val quizId = "testeQuizId"
    QuizAddPerguntaAdminScreen(navController, quizId)
}
