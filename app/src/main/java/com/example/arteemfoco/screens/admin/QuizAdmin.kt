package com.example.arteemfoco.screens.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arteemfoco.AdminScreenScaffold
import com.example.arteemfoco.Screen
import com.google.firebase.firestore.FirebaseFirestore

data class Quiz(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val alternatives: List<String> = emptyList(),
)

@Composable
fun QuizAdminScreen(navController: NavHostController) {
    AdminScreenScaffold(navController) { innerPadding ->
        val db = FirebaseFirestore.getInstance()
        var quizzes by remember { mutableStateOf(listOf<com.example.arteemfoco.screens.admin.Quiz>()) }
        var isLoading by remember { mutableStateOf(true) }

        // Busca os quizzes no Firestore
        LaunchedEffect(Unit) {
            db.collection("quizzes")
                .get()
                .addOnSuccessListener { result ->
                    val quizzesList = result.map { doc ->
                        com.example.arteemfoco.screens.admin.Quiz(
                            title = doc.getString("title") ?: "",
                            description = doc.getString("description") ?: "",
                            alternatives = doc.get("alternatives") as? List<String> ?: emptyList(),
                            id = doc.id // Armazenamos o ID aqui
                        )
                    }
                    quizzes = quizzesList
                    isLoading = false
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Erro ao buscar quizzes: ", exception)
                }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            Text(
                text = "Quiz",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp) // Espaçamento do topo
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading) {
                    CircularProgressIndicator()  // Mostra o indicador de carregamento
                } else {
                    quizzes.forEach { quiz ->
                        QuizCardAdmin(
                            title = quiz.title,
                            quizId = quiz.id,
                            onDelete = { quizId -> quizzes = quizzes.filter { it.id != quizId } },
                            navController = navController
                        )
                        Spacer(Modifier.height(10.dp))
                    }

                    Spacer(Modifier.height(20.dp))
                }
            }

            // Botão flutuante
            FloatingActionButton(
                onClick = { navController.navigate(Screen.QuizAddAdmin.route) },
                backgroundColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

        }
    }
}

@Composable
fun QuizCardAdmin(
    title: String,
    quizId: String,
    onDelete: (String) -> Unit,
    navController: NavController,
) {
    val db = FirebaseFirestore.getInstance()

    Box(
        modifier = Modifier
            .width(350.dp)
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .height(120.dp)
            .clickable {
                navController.navigate("quizViewScreen/$quizId")
            }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            // Coluna com título e subtítulo à direita
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 35.dp, end = 35.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Ícone de exclusão
        Box(
            modifier = Modifier
                .padding(end = 20.dp, top = 8.dp, start = 5.dp)
                .align(Alignment.CenterEnd)
                .clickable {
                    // Exclui o quiz do Firestore
                    db
                        .collection("quizzes")
                        .document(quizId)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("Firestore", "Quiz excluído com sucesso!")
                            onDelete(quizId) // Atualiza a lista de quizzes na tela
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Erro ao excluir quiz", e)
                        }
                }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

    }
}

@Preview
@Composable
private fun QuizAdminPreview() {
    val navController = rememberNavController()
    QuizAdminScreen(navController)
}
