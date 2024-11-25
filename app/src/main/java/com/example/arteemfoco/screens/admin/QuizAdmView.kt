package com.example.arteemfoco.screens.admin
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Perguntas para $quizId", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        questions.forEach { question ->
            Text(text = "Título: ${question.title}", fontSize = 18.sp)
            Text(text = "Descrição: ${question.description}", fontSize = 14.sp)
            Text(text = "Alternativas:", fontSize = 14.sp)
            question.alternatives.forEachIndexed { index, alternative ->
                Text(
                    text = "- ${alternative} ${if (index == question.correctAnswerIndex) "(Correta)" else ""}",
                    fontSize = 14.sp
                )
            }
            Spacer(Modifier.height(16.dp))
        }

        Button(
            onClick = { navController.navigate("quizAddPerguntaAdminScreen/$quizId") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Adicionar Pergunta")
        }
    }
}


//@Composable
//fun QuizAdminViewScreen(navController: NavController, quizId: String) {
//    // Aqui você pode buscar as perguntas e alternativas para o `quizId` do Firestore
//    var questions by remember { mutableStateOf(listOf<Question>()) }  // Defina o tipo correto para as perguntas
//
//    LaunchedEffect(quizId) {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("quizzes/$quizId/perguntas")
//            .get()
//            .addOnSuccessListener { result ->
//                val questionsList = result.map { doc ->
//                    Question(
//                        title = doc.getString("title") ?: "",
//                        description = doc.getString("description") ?: "",
//                        alternatives = doc.get("alternatives") as? List<String> ?: emptyList(),
//                        correctAnswerIndex = doc.getLong("correctAnswerIndex")?.toInt()
//                    )
//                }
//                questions = questionsList
//            }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(text = "Perguntas para $quizId", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//
//        Spacer(Modifier.height(16.dp))
//
//        questions.forEach { question ->
//            Text(text = "Título: ${question.title}", fontSize = 18.sp)
//            Text(text = "Descrição: ${question.description}", fontSize = 14.sp)
//            Text(text = "Alternativas:", fontSize = 14.sp)
//            question.alternatives.forEachIndexed { index, alternative ->
//                Text(
//                    text = "- ${alternative} ${if (index == question.correctAnswerIndex) "(Correta)" else ""}",
//                    fontSize = 14.sp
//                )
//            }
//            Spacer(Modifier.height(16.dp))
//        }
//
//        Button(
//            onClick = { navController.navigate("quizAddPerguntaAdminScreen/$quizId") },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Adicionar Pergunta")
//        }
//    }
//}

//@Composable
//fun QuizAdminViewScreen(navController: NavController, quizId: String) {
//    var questions by remember { mutableStateOf(listOf<Map<String, Any>>()) }
//
//    LaunchedEffect(quizId) {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("quizzes/$quizId/perguntas")
//            .get()
//            .addOnSuccessListener { result ->
//                questions = result.map { doc -> doc.data }
//            }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Detalhes do Quiz",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            questions.forEachIndexed { index, question ->
//                Text(
//                    text = "Pergunta ${index + 1}: ${question["title"]}",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                (question["alternatives"] as? List<String>)?.forEachIndexed { i, alternative ->
//                    Text(
//                        text = "Alternativa ${i + 1}: $alternative",
//                        fontSize = 16.sp,
//                        modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
//                    )
//                }
//                Spacer(Modifier.height(16.dp))
//            }
//
//            Button(
//                onClick = { navController.navigate("quizAddPerguntaAdminScreen/$quizId") },
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            ) {
//                Text("Adicionar Pergunta")
//            }
//        }
//    }
//}
@Preview
@Composable
private fun QuizAdminViewPreview() {
    val navController = rememberNavController()
    QuizAdminViewScreen(navController, "quiz123")
}
