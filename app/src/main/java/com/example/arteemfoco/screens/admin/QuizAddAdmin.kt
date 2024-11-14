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
            TextField(value = title, onValueChange = { title = it })

            Spacer(Modifier.height(16.dp))

            // Descrição de Áudio
            Text("Descrição de Áudio", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            TextField(value = audioDescription, onValueChange = { audioDescription = it })

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                val db = FirebaseFirestore.getInstance()
                val quizRef = db.collection("quizzes").document() // cria um ID único para o quiz

                val quizData = mapOf(
                    "title" to title,
                    "audioDescription" to audioDescription,
                    "id" to quizRef.id
                )

                quizRef.set(quizData).addOnSuccessListener {
                    navController.navigate("quizAdminScreen/${quizRef.id}") // Passa o ID do quiz para a próxima tela
                }.addOnFailureListener {
                    // Tratamento de erro
                }
            }) {
                Text("Salvar Quiz")
            }
        }
    }
}


//@Composable
//fun QuizAddAdminScreen(navController: NavController) {
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var audioDescription by remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        // Botão de Voltar
//        Box(
//            modifier = Modifier.padding(start = 17.dp, top = 37.dp),
//            contentAlignment = Alignment.TopStart
//        ) {
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Voltar",
//                tint = Color.Black,
//                modifier = Modifier
//                    .clickable { navController.popBackStack() }
//            )
//        }
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Text("Título do Quiz", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            TextField(value = title, onValueChange = { title = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            Text("Descrição de Áudio", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            TextField(value = audioDescription, onValueChange = { audioDescription = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            Button(onClick = {
//                val db = FirebaseFirestore.getInstance()
//                val quizRef = db.collection("quizzes").document()
//
//                val quizData = mapOf(
//                    "title" to title,
//                    "audioDescription" to audioDescription,
//                    "id" to quizRef.id
//                )
//
//                quizRef.set(quizData).addOnSuccessListener {
//                    navController.navigate("quizAdminViewScreen/${quizRef.id}")
//                }.addOnFailureListener {
//                    // Tratamento de erro
//                }
//            }) {
//                Text("Salvar Quiz")
//            }
//        }
//    }
//}

//@Composable
//fun QuizAddAdminScreen(navController: NavController) {
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var audioDescription by remember { mutableStateOf("") }
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
//            // Título do Quiz
//            Text("Título do Quiz", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            TextField(value = title, onValueChange = { title = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            // Descrição de Áudio
//            Text("Descrição de Áudio", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            TextField(value = audioDescription, onValueChange = { audioDescription = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            Button(onClick = {
//                val db = FirebaseFirestore.getInstance()
//                val quizRef = db.collection("quizzes").document() // cria um ID único para o quiz
//
//                val quizData = mapOf(
//                    "title" to title,
//                    "audioDescription" to audioDescription,
//                    "id" to quizRef.id
//                )
//
//                quizRef.set(quizData).addOnSuccessListener {
//                    navController.navigate("quizAdminScreen/${quizRef.id}") // Passa o ID do quiz para a próxima tela
//                }.addOnFailureListener {
//                    // Tratamento de erro
//                }
//            }) {
//                Text("Salvar Quiz")
//            }
//        }
//    }
//}
@Composable
@Preview
fun QuizScreenPreview() {
    val navController = rememberNavController()
    QuizScreen(navController)
}

//package com.example.arteemfoco.screens.admin
//

//
//
//@Composable
//fun QuizAddAdminScreen(navController: NavController) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White) // Fundo branco para toda a tela
//    ) {
//        // Ícone de voltar no canto superior esquerdo
//        Box(
//            modifier = Modifier.padding(start = 17.dp, top = 37.dp),
//            contentAlignment = Alignment.BottomEnd
//        ) {
//            Icon(
//                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                contentDescription = "Voltar",
//                tint = Color.Black,
//                modifier = Modifier
//                    .clickable { navController.popBackStack() }
//            )
//        }
//
//        // Texto "Salvar" no canto superior direito
//        Text(
//            text = "Salvar",
//            fontSize = 16.sp,
//            color = Color.Blue,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(top = 37.dp, end = 17.dp)
//                .clickable {
//                    navController.popBackStack()
//                }
//        )
//
//        Text(
//            text = "Novo Quiz",
//            fontSize = 19.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = 40.dp),
//            textAlign = TextAlign.Center
//        )
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 0.dp) // Adiciona espaço suficiente para o título
//        ) {
//            // Imagem
//            Box(
//                modifier = Modifier
//                    .background(Color.Gray)
//                    .size(300.dp, 150.dp)
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            // Labels e Caixas de Texto
//            Text(text = "Título", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//
//            // Simulação de uma caixa de texto
//            Box(
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .size(300.dp, 40.dp)
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            Text(text = "Obra", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//
//            // Simulação de um dropdown
//            Box(
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .size(300.dp, 40.dp)
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            Text(text = "Perguntas", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//
//            // Perguntas Alternatives
//            Column(
//                modifier = Modifier.clickable { },
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                AlternativeAdmin("Quiz 1", "Descrição da pergunta 1", {})
//                AlternativeAdmin("Quiz 2", "Descrição da pergunta 2", {})
//                AlternativeAdmin("Quiz 3", "Descrição da pergunta 3", {})
//            }
//        }
//
//        // Botão de "Adicionar Pergunta"
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 50.dp, end = 20.dp),
//            contentAlignment = Alignment.BottomEnd
//        ) {
//            Button(
//                onClick = {navController.navigate("quizAddPerguntaAdminScreen")}
//            ) {
//                Text(text = "Adicionar Pergunta")
//            }
//        }
//    }
//}
//
//
//

//
//@Composable
//fun AlternativeAdmin(title: String, subtitle: String, onDelete: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .width(300.dp) // Largura total da caixa principal
//            .background(
//                Color.Gray,
//                shape = RoundedCornerShape(16.dp)
//            )
//            .height(70.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize() // Preencher a caixa cinza
//        ) {
//            // Caixa escura à esquerda
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(100.dp) // Largura da caixa escura
//                    .background(
//                        Color.DarkGray,
//                        shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
//                    ) // Cor de fundo escura e bordas arredondadas
//            )
//
//            // Coluna com título e subtítulo à direita
//            Column(
//                modifier = Modifier
//                    .weight(1f) // Preencher o restante do espaço
//                    .padding(start = 16.dp), // Espaçamento entre a caixa escura e a coluna
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.Start,
//            ) {
//                Text(
//                    text = title,
//                    fontSize = 15.sp,
//                    color = Color.White,
//                )
//
//                Spacer(Modifier.height(2.dp))
//
//                Text(
//                    text = subtitle,
//                    fontSize = 11.sp,
//                    color = Color.White,
//                )
//            }
//
//            // Botão de lixeira à direita
//            IconButton(
//                onClick = onDelete, // Ação ao clicar no botão
//                modifier = Modifier.align(Alignment.CenterVertically) // Alinha a lixeira verticalmente
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Delete,
//                    contentDescription = "Delete",
//                    tint = Color.White
//                )
//            }
//        }
//    }
//}