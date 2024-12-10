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

@Composable
fun QuizAddAdminScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var audioDescription by remember { mutableStateOf("") }

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
                text = "Criar Quiz",
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
            // Título do Quiz
            Text(
                text = "Título do Quiz",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite o título do quiz") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descrição de Áudio
            Text(
                text = "Descrição de Áudio",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = audioDescription,
                onValueChange = { audioDescription = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite a descrição do áudio") }
            )
        }

        // Botão de "Salvar Quiz"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 40.dp)
        ) {
            Button(
                onClick = {
                    val db = FirebaseFirestore.getInstance()
                    val quizRef = db.collection("quizzes").document()
                    val code = quizRef.id.take(4) // Use os primeiros 4 caracteres como código

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
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Quiz", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuizAddAdminScreenPreview() {
    val navController = rememberNavController()
    QuizAddAdminScreen(navController)
}
