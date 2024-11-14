package com.example.arteemfoco.screens.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
//import com.example.arteemfoco.screens.quizzes.QuizCard
//import com.example.arteemfoco.screens.quizzes.Quiz
import com.google.firebase.firestore.FirebaseFirestore

data class Quiz(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val alternatives: List<String> = emptyList()
)
@Composable
fun QuizAdminScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    var quizzes by remember { mutableStateOf(listOf<Quiz>()) }

    LaunchedEffect(Unit) {
        db.collection("quizzes")
            .get()
            .addOnSuccessListener { result ->
                val quizzesList = result.map { doc ->
                    Quiz(
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        alternatives = doc.get("alternatives") as? List<String> ?: emptyList(),
                        id = doc.id // Armazenamos o ID aqui
                    )
                }
                quizzes = quizzesList
            }
            .addOnFailureListener {
                // Tratamento de erro, exibir mensagem ao usuário se necessário
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Meus Quizzes",
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 70.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(top = 100.dp)
        ) {
            quizzes.forEach { quiz ->
                Text(
                    text = quiz.title,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            // Navega para a tela de detalhes do quiz, passando o ID do quiz
                            navController.navigate("quizAdminViewScreen/${quiz.id}") // Passa o ID correto
                        }
                )
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("quizAddAdminScreen") },
            backgroundColor = Color.Gray,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

//@Composable
//fun QuizAdminScreen(navController: NavController) {
//    val db = FirebaseFirestore.getInstance()
//    var quizzes by remember { mutableStateOf(listOf<Quiz>()) }
//
//    LaunchedEffect(Unit) {
//        db.collection("quizzes")
//            .get()
//            .addOnSuccessListener { result ->
//                val quizzesList = result.map { doc ->
//                    Quiz(
//                        id = doc.getString("id") ?: "",  // Certifique-se de que o ID seja capturado
//                        title = doc.getString("title") ?: "",
//                        description = doc.getString("description") ?: ""
//                    )
//                }
//                quizzes = quizzesList
//            }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Text(
//            text = "Meus Quizzes",
//            fontSize = 24.sp,
//            color = Color.Black,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = 70.dp)
//        )
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxSize().padding(top = 100.dp)
//        ) {
//            quizzes.forEach { quiz ->
//                Text(
//                    text = quiz.title,
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .clickable {
//                            navController.navigate("quizAdminViewScreen/${quiz.id}")
//                        }
//                )
//            }
//        }
//
//        FloatingActionButton(
//            onClick = { navController.navigate("quizAddAdminScreen") },
//            backgroundColor = Color.Gray,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(20.dp)
//        ) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
//        }
//    }
//}


//@Composable
//fun QuizAdminScreen(navController: NavController) {
//    val db = FirebaseFirestore.getInstance()
//    var quizzes by remember { mutableStateOf(listOf<Quiz>()) }
//
//    LaunchedEffect(Unit) {
//        db.collection("quizzes")
//            .get()
//            .addOnSuccessListener { result ->
//                val quizzesList = result.map { doc ->
//                    Quiz(
//                        title = doc.getString("title") ?: "",
//                        description = doc.getString("description") ?: "",
//                        alternatives = doc.get("alternatives") as? List<String> ?: emptyList()
//                    )
//                }
//                quizzes = quizzesList
//            }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Text(
//            text = "Meus Quizzes",
//            fontSize = 24.sp,
//            color = Color.Black,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = 70.dp)
//        )
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxSize().padding(top = 100.dp)
//        ) {
//            quizzes.forEach { quiz ->
//                Text(
//                    text = quiz.title,
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .clickable {
//                            // Navega para a tela de detalhes do quiz, passando o ID do quiz
//                            navController.navigate("quizAdminViewScreen/${quiz.title}")
//                        }
//                )
//            }
//        }
//
//        FloatingActionButton(
//            onClick = { navController.navigate("quizAddAdminScreen") },
//            backgroundColor = Color.Gray,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(20.dp)
//        ) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
//        }
//    }
//}

//data class Quiz(
//    val title: String = "",
//    val description: String = "",
//    val alternatives: List<String> = emptyList()
//)
//@Composable
//fun QuizAdminScreen(navController: NavController) {
//    val db = FirebaseFirestore.getInstance()
//    var quizzes by remember { mutableStateOf(listOf<Quiz>()) }
//
//    LaunchedEffect(Unit) {
//        db.collection("quizzes")
//            .get()
//            .addOnSuccessListener { result ->
//                val quizzesList = result.map { doc ->
//                    Quiz(
//                        title = doc.getString("title") ?: "",
//                        description = doc.getString("description") ?: "",
//                        alternatives = doc.get("alternatives") as? List<String> ?: emptyList()
//                    )
//                }
//                quizzes = quizzesList
//            }
//            .addOnFailureListener { exception ->
//                Log.w("Firestore", "Erro ao buscar quizzes: ", exception)
//            }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//
//        Text(
//            text = "Meus Quizzes",
//            fontSize = 24.sp,
//            color = Color.Black,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = 70.dp)
//        )
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxSize().padding(top = 100.dp)
//        ) {
//            quizzes.forEach { quiz ->
////                QuizCard(title = quiz.title, subtitle = quiz.description)
////                Spacer(Modifier.height(10.dp))
//                Text(
//                    text = quiz.title,
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .clickable {
//
//                            navController.navigate("quizAddPerguntaAdminScreen/${quiz.title}")
//                        }
//                )
//            }
//        }
//
//        FloatingActionButton(
//            onClick = { navController.navigate("quizAddAdminScreen") },
//            backgroundColor = Color.Gray,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(20.dp)
//        ) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
//        }
//    }
//}

//@Composable
//fun QuizAdminScreen(navController: NavController) {
//    val db = FirebaseFirestore.getInstance()
//    var quizzes by remember { mutableStateOf(listOf<Quiz>()) }
//
//    // Carrega os quizzes do Firebase
//    LaunchedEffect(Unit) {
//        db.collection("quizzes")
//            .get()
//            .addOnSuccessListener { result ->
//                val quizzesList = result.map { doc ->
//                    Quiz(
//                        title = doc.getString("title") ?: "",
//                        description = doc.getString("description") ?: "",
//                        alternatives = doc.get("alternatives") as? List<String> ?: emptyList()
//                    )
//                }
//                quizzes = quizzesList
//            }
//            .addOnFailureListener { exception ->
//                Log.w("Firestore", "Erro ao buscar quizzes: ", exception)
//            }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Text("Meus Quizzes",
//                fontSize = 24.sp,
//                color = Color.Black,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(top = 40.dp)
//            )
//
//            quizzes.forEach { quiz ->
//                Text(
//                    text = quiz.title, // Corrigido para acessar o título da obra
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .clickable {
//                            // Passe o id ou o próprio objeto quiz para a navegação
//                            navController.navigate("quizAddPerguntaAdminScreen/${quiz.title}")
//                        }
//                )
//            }
//        }
//
//        FloatingActionButton(
//            onClick = { navController.navigate("quizAddAdminScreen") },
//            backgroundColor = Color.Gray,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(20.dp)
//        ) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
//        }
//    }
//}

//data class Quiz(
//    val title: String = "",
//    val description: String = "",
//    val alternatives: List<String> = emptyList()
//)
//@Composable
//fun QuizAdminScreen(navController: NavController) {
//    val db = FirebaseFirestore.getInstance()
//    var quizzes by remember { mutableStateOf(listOf<Quiz>()) }
//
//    // Carrega os quizzes do Firebase
//    LaunchedEffect(Unit) {
//        db.collection("quizzes")
//            .get()
//            .addOnSuccessListener { result ->
//                val quizzesList = result.map { doc ->
//                    Quiz(
//                        title = doc.getString("title") ?: "",
//                        description = doc.getString("description") ?: "",
//                        alternatives = doc.get("alternatives") as? List<String> ?: emptyList()
//                    )
////                    doc.id to (doc.getString("title") ?: "Untitled Quiz")
//                }
//                quizzes = quizzesList
//            }
//            .addOnFailureListener { expection ->
//                Log.w("Firestore", "Erro ao buscar quizzes: ", expection)
//            }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Text("Quizzes", fontSize = 24.sp, color = Color.Black, modifier = Modifier.padding(16.dp))
//
//            quizzes.forEach { (quizId, quizTitle) ->
//                Text(
//                    text = quizTitle,
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .clickable { navController.navigate("quizAddPerguntaAdminScreen/$quizId") }
//                )
//            }
//        }
//
//        FloatingActionButton(
//            onClick = { navController.navigate("quizAddAdminScreen") },
//            backgroundColor = Color.Gray,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(20.dp)
//        ) {
//            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
//        }
//    }
//}

//LaunchedEffect(Unit) {
//    val db = FirebaseFirestore.getInstance()
//    db.collection("quizzes")
//        .get()
//        .addOnSuccessListener { result ->
//            quizzes = result.documents.map { doc ->
//                doc.id to (doc.getString("title") ?: "Untitled Quiz")
//            }
//        }
//}
//package com.example.arteemfoco.screens.admin
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.FloatingActionButton
//import androidx.compose.material.Icon
//import androidx.compose.material.Text
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.arteemfoco.screens.obras.ObraCard
//
//@Composable
//fun QuizAdminScreen(navController: NavController) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White) // Fundo branco para toda a tela
//    ) {
//        Text(
//            text = "Quiz",
//            fontSize = 19.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = 40.dp) // Espaçamento do topo
//        )
//
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 0.dp) // Adiciona espaço suficiente para o título
//        ) {
//            Column(
//                modifier = Modifier.clickable { },
//                verticalArrangement = Arrangement.spacedBy(3.dp)
//            ) {
//                ObraCard("Quiz 1", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
//                Spacer(Modifier.height(10.dp))
//                ObraCard("Quiz 2", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
//                Spacer(Modifier.height(10.dp))
//                ObraCard("Quiz 3", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
//                Spacer(Modifier.height(10.dp))
//                ObraCard("Quiz 4", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
//            }
//        }
//
//        FloatingActionButton(
//            onClick = { navController.navigate("quizAddAdminScreen") },
//            backgroundColor = Color.Gray,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(bottom = 50.dp, end = 20.dp) // Padding para afastar do canto
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = "Add",
//                tint = Color.White
//            )
//        }
//
//    }
//}
//
@Preview
@Composable
private fun QuizAdminPreview() {
    val navController = rememberNavController()
    QuizAdminScreen(navController)
}

