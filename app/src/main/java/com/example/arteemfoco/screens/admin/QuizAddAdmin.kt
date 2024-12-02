package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.arteemfoco.screens.quiz.QuizScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@Composable
fun QuizAddAdminScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var audioDescription by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Ícone de voltar no canto superior esquerdo
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Voltar",
            tint = Color.Black,
            modifier = Modifier
                .padding(start = 17.dp, top = 37.dp)
                .clickable { navController.popBackStack() }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Título do Quiz
            Text("Título do Quiz", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            TextField(value = title, onValueChange = { title = it })

            Spacer(Modifier.height(16.dp))

            // Descrição de Áudio
            Text("Descrição de Áudio", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            TextField(value = audioDescription, onValueChange = { audioDescription = it })

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                val db = FirebaseFirestore.getInstance()
                val quizRef = db.collection("quizzes").document()
                val code = quizRef.id.take(4) // Use os primeiros 6 caracteres como código

                val quizData = mapOf(
                    "title" to title,
                    "audioDescription" to audioDescription,
                    "id" to quizRef.id,
                    "code" to code
                )

                quizRef.set(quizData).addOnSuccessListener {
                    navController.navigate("quizAdminScreen/${quizRef.id}")
                }.addOnFailureListener {
                    // Tratamento de erro
                }
            }) {
                Text("Salvar Quiz")
            }

        }
    }
}

@Composable
@Preview
fun QuizScreenPreview() {
    val navController = rememberNavController()
//    QuizScreen(navController)
}
