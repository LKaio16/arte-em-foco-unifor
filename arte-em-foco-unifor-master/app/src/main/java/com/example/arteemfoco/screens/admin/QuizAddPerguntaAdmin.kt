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

//@Composable
//fun QuizAddPerguntaAdminScreen(navController: NavController, quizId: String) {
//    var questionTitle by remember { mutableStateOf("") }
//    var questionDescription by remember { mutableStateOf("") }
//    var alternatives by remember { mutableStateOf(listOf<String>()) }
//    var alternativeText by remember { mutableStateOf("") }
//    var correctAnswerIndex by remember { mutableStateOf<Int?>(null) }
//
//    // Adicionar alternativa
//    Button(onClick = {
//        if (alternativeText.isNotEmpty()) {
//            alternatives = alternatives + alternativeText
//            alternativeText = ""
//        }
//    }) {
//        Text("Adicionar Alternativa")
//    }
//
//    // Exibir alternativas e marcar a correta
//    alternatives.forEachIndexed { index, alternative ->
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(16.dp)
//        ) {
//            RadioButton(
//                selected = correctAnswerIndex == index,
//                onClick = { correctAnswerIndex = index }
//            )
//            Text(text = alternative)
//        }
//    }
//
//    // Salvar pergunta no Firestore
//    Button(onClick = {
//        val db = FirebaseFirestore.getInstance()
//
//        val questionData = mapOf(
//            "title" to questionTitle,
//            "description" to questionDescription,
//            "alternatives" to alternatives,
//            "correctAnswerIndex" to correctAnswerIndex
//        )
//
//        db.collection("quizzes/$quizId/perguntas")
//            .add(questionData)
//            .addOnSuccessListener {
//                navController.popBackStack()  // Volta para a tela anterior
//            }
//            .addOnFailureListener {
//                // Tratar erro
//            }
//    }) {
//        Text("Salvar Pergunta")
//    }
//}

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
        Box(
            modifier = Modifier.padding(start = 17.dp, top = 37.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
            )
        }

        // Texto "Salvar"
        Text(
            text = "Salvar",
            fontSize = 18.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 37.dp, end = 17.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp) // Ajuste para centralizar e dar mais espaço
        ) {
            // Imagem
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .size(250.dp, 150.dp) // Ajuste do tamanho da imagem
            )

            Spacer(Modifier.height(32.dp)) // Espaço entre a imagem e o título

            // Título
            Text(text = "Título da Pergunta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = questionTitle,
                onValueChange = { questionTitle = it },
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 56.dp),
                placeholder = { Text("Digite o título da pergunta") }
            )

            Spacer(Modifier.height(16.dp))

            // Descrição
            Text(text = "Descrição da Pergunta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = questionDescription,
                onValueChange = { questionDescription = it },
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 56.dp),
                placeholder = { Text("Digite a descrição da pergunta") }
            )

            Spacer(Modifier.height(16.dp))

            // Add alter
            Text("Alternativas", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = alternativeText,
                onValueChange = { alternativeText = it },
                label = { Text("Nova alternativa") },
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 56.dp)
            )

            Spacer(Modifier.height(13.dp))

            Button(onClick = {
                if (alternativeText.isNotEmpty()) {
                    alternatives = alternatives + alternativeText
                    alternativeText = ""
                }
            }) {
                Text("Adicionar Alternativa")
            }

            Spacer(Modifier.height(16.dp))

            // alternativas adicionadas com os RadioButtons
            alternatives.forEachIndexed { index, alternative ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    RadioButton(
                        selected = correctAnswerIndex == index,
                        onClick = { correctAnswerIndex = index }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = alternative, fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(32.dp))

            // Botão de "Salvar Pergunta"
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp, end = 20.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = {
                        val db = FirebaseFirestore.getInstance()

                        // Dados da pergunta, incluindo a alternativa correta
                        val questionData = mapOf(
                            "title" to questionTitle,
                            "description" to questionDescription,
                            "alternatives" to alternatives, // Armazena as alternativas
                            "correctAnswerIndex" to correctAnswerIndex // Armazena o índice da alternativa correta
                        )

                        // Salva a pergunta com as alternativas no Firestore
                        db.collection("quizzes/$quizId/perguntas")
                            .add(questionData)
                            .addOnSuccessListener {
                                navController.popBackStack() // Volta para a tela anterior
                            }
                            .addOnFailureListener {
                                // Tratamento de erro
                            }
                    }
                ) {
                    Text("Salvar Pergunta")
                }
            }
        }
    }
}

//@Composable
//fun QuizAddPerguntaAdminScreen(navController: NavController, quizId: String) {
//    var questionTitle by remember { mutableStateOf("") }
//    var questionDescription by remember { mutableStateOf("") }
//    var alternatives by remember { mutableStateOf(listOf<String>()) } // Lista de alternativas
//    var alternativeText by remember { mutableStateOf("") } // Texto da nova alternativa
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
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
//            fontSize = 18.sp,
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
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 110.dp) // Ajuste para centralizar e dar mais espaço
//        ) {
//            // Imagem
//            Box(
//                modifier = Modifier
//                    .background(Color.Gray)
//                    .size(250.dp, 150.dp) // Ajuste do tamanho da imagem
//            )
//
//            Spacer(Modifier.height(32.dp)) // Espaço entre a imagem e o título
//
//            // Título da Pergunta
//            Text(text = "Título da Pergunta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//            TextField(
//                value = questionTitle,
//                onValueChange = { questionTitle = it },
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .size(300.dp, 56.dp),
//                placeholder = { Text("Digite o título da pergunta") }
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            // Descrição da Pergunta
//            Text(text = "Descrição da Pergunta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//            TextField(
//                value = questionDescription,
//                onValueChange = { questionDescription = it },
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .size(300.dp, 56.dp),
//                placeholder = { Text("Digite a descrição da pergunta") }
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            // Adicionando alternativas
//            Text("Alternativas", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//            TextField(
//                value = alternativeText,
//                onValueChange = { alternativeText = it },
//                label = { Text("Nova alternativa") },
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .size(300.dp, 56.dp)
//            )
//
//            Spacer(Modifier.height(13.dp))
//
//            Button(onClick = {
//                if (alternativeText.isNotEmpty()) {
//                    alternatives = alternatives + alternativeText // Adiciona a alternativa à lista
//                    alternativeText = "" // Limpa o campo de entrada
//                }
//            }) {
//                Text("Adicionar Alternativa")
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            // Exibe as alternativas adicionadas
//            alternatives.forEachIndexed { index, alternative ->
//                Text("Alternativa ${index + 1}: $alternative", fontSize = 14.sp)
//            }
//
//            Spacer(Modifier.height(32.dp)) // Espaço antes do botão de salvar
//
//            // Botão de "Salvar Pergunta com Alternativas"
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(bottom = 50.dp, end = 20.dp),
//                contentAlignment = Alignment.BottomEnd
//            ) {
//                Button(
//                    onClick = {
//                        val db = FirebaseFirestore.getInstance()
//
//                        // Dados da pergunta
//                        val questionData = mapOf(
//                            "title" to questionTitle,
//                            "description" to questionDescription,
//                            "alternatives" to alternatives // Armazena as alternativas
//                        )
//
//                        // Salva a pergunta com as alternativas no Firestore
//                        db.collection("quizzes/$quizId/perguntas")
//                            .add(questionData)
//                            .addOnSuccessListener {
//                                navController.popBackStack() // Volta para a tela anterior
//                            }
//                            .addOnFailureListener {
//                                // Tratamento de erro
//                            }
//                    }
//                ) {
//                    Text("Salvar Pergunta")
//                }
//            }
//        }
//    }
//}

@Composable
@Preview
fun QuizAddPerguntaAdminScreenPreview() {
    val navController = rememberNavController()
    QuizAddPerguntaAdminScreen(navController, "quiz123")
}

//@Composable
//fun QuizAddPerguntaAdminScreen(navController: NavController, quizId: String) {
//    var questionTitle by remember { mutableStateOf("") }
//    var questionDescription by remember { mutableStateOf("") }
//    var alternatives by remember { mutableStateOf(listOf<String>()) } // Lista de alternativas
//    var alternativeText by remember { mutableStateOf("") } // Texto da nova alternativa
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
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
//            text = "Nova Pergunta",
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
//                .padding(top = 0.dp)
//        ) {
//            // Título da Pergunta
//            Text(text = "Título da Pergunta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//            TextField(
//                value = questionTitle,
//                onValueChange = { questionTitle = it },
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .size(300.dp, 56.dp),
//                placeholder = { Text("Digite o título da pergunta") }
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            // Descrição da Pergunta
//            Text(text = "Descrição da Pergunta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//            TextField(
//                value = questionDescription,
//                onValueChange = { questionDescription = it },
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .size(300.dp, 56.dp),
//                placeholder = { Text("Digite a descrição da pergunta") }
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            // Adicionando alternativas
//            Text("Alternativas", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//            TextField(
//                value = alternativeText,
//                onValueChange = { alternativeText = it },
//                label = { Text("Nova alternativa") },
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .size(300.dp, 56.dp)
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            Button(onClick = {
//                if (alternativeText.isNotEmpty()) {
//                    alternatives = alternatives + alternativeText // Adiciona a alternativa à lista
//                    alternativeText = "" // Limpa o campo de entrada
//                }
//            }) {
//                Text("Adicionar Alternativa")
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            // Exibe as alternativas adicionadas
//            alternatives.forEachIndexed { index, alternative ->
//                Text("Alternativa ${index + 1}: $alternative", fontSize = 14.sp)
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            // Botão de "Salvar Pergunta com Alternativas"
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(bottom = 50.dp, end = 20.dp),
//                contentAlignment = Alignment.BottomEnd
//            ) {
//                Button(
//                    onClick = {
//                        val db = FirebaseFirestore.getInstance()
//
//                        // Dados da pergunta
//                        val questionData = mapOf(
//                            "title" to questionTitle,
//                            "description" to questionDescription,
//                            "alternatives" to alternatives // Armazena as alternativas
//                        )
//
//                        // Salva a pergunta com as alternativas no Firestore
//                        db.collection("quizzes/$quizId/perguntas")
//                            .add(questionData)
//                            .addOnSuccessListener {
//                                navController.popBackStack() // Volta para a tela anterior
//                            }
//                            .addOnFailureListener {
//                                // Tratamento de erro
//                            }
//                    }
//                ) {
//                    Text("Salvar Pergunta com Alternativas")
//                }
//            }
//        }
//    }
//}

//@Composable
//@Preview
//fun QuizAddPerguntaAdminScreenPreview() {
//    val navController = rememberNavController()
//    QuizAddPerguntaAdminScreen(navController, "quiz123")
//}

//@Composable
//fun QuizAddPerguntaAdminScreen(navController: NavController, quizId: String) {
//    var questionTitle by remember { mutableStateOf("") }
//    var questionDescription by remember { mutableStateOf("") }
//    var alternatives by remember { mutableStateOf(listOf<String>()) } // Lista de alternativas
//    var alternativeText by remember { mutableStateOf("") } // Texto da nova alternativa
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
//            // Título da Pergunta
//            Text("Título da Pergunta", fontSize = 16.sp)
//            TextField(value = questionTitle, onValueChange = { questionTitle = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            // Descrição da Pergunta
//            Text("Descrição da Pergunta", fontSize = 16.sp)
//            TextField(value = questionDescription, onValueChange = { questionDescription = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            // Adicionando alternativas
//            Text("Alternativas", fontSize = 16.sp)
//            TextField(
//                value = alternativeText,
//                onValueChange = { alternativeText = it },
//                label = { Text("Nova alternativa") }
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            Button(onClick = {
//                if (alternativeText.isNotEmpty()) {
//                    alternatives = alternatives + alternativeText // Adiciona a alternativa à lista
//                    alternativeText = "" // Limpa o campo de entrada
//                }
//            }) {
//                Text("Adicionar Alternativa")
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            // Exibe as alternativas adicionadas
//            alternatives.forEachIndexed { index, alternative ->
//                Text("Alternativa ${index + 1}: $alternative", fontSize = 14.sp)
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            Button(onClick = {
//                val db = FirebaseFirestore.getInstance()
//
//                // Dados da pergunta
//                val questionData = mapOf(
//                    "title" to questionTitle,
//                    "description" to questionDescription,
//                    "alternatives" to alternatives // Armazena as alternativas
//                )
//
//                // Salva a pergunta com as alternativas no Firestore
//                db.collection("quizzes/$quizId/perguntas")
//                    .add(questionData)
//                    .addOnSuccessListener {
//                        navController.popBackStack() // Volta para a tela anterior
//                    }
//                    .addOnFailureListener {
//                        // Tratamento de erro
//                    }
//            }) {
//                Text("Salvar Pergunta com Alternativas")
//            }
//        }
//    }
//}


//@Composable
//fun QuizAddPerguntaAdminScreen(navController: NavController, quizId: String) {
//    var questionTitle by remember { mutableStateOf("") }
//    var questionDescription by remember { mutableStateOf("") }
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
//            // Título da Pergunta
//            Text("Título da Pergunta", fontSize = 16.sp)
//            TextField(value = questionTitle, onValueChange = { questionTitle = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            // Descrição da Pergunta
//            Text("Descrição da Pergunta", fontSize = 16.sp)
//            TextField(value = questionDescription, onValueChange = { questionDescription = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            Button(onClick = {
//                val db = FirebaseFirestore.getInstance()
//
//                val questionData = mapOf(
//                    "title" to questionTitle,
//                    "description" to questionDescription
//                )
//
//                // Salva a pergunta na coleção de perguntas do quiz específico
//                db.collection("quizzes/$quizId/perguntas")
//                    .add(questionData)
//                    .addOnSuccessListener {
//                        navController.popBackStack() // Volta para a tela anterior
//                    }
//                    .addOnFailureListener {
//                        // Tratamento de erro
//                    }
//            }) {
//                Text("Salvar Pergunta")
//            }
//        }
//    }
//}

//fun QuizAddPerguntaAdminScreen(navController: NavController, quizId: String) {
//    var questionTitle by remember { mutableStateOf("") }
//    var questionDescription by remember { mutableStateOf("") }
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
//            // Título da Pergunta
//            Text("Título da Pergunta", fontSize = 16.sp)
//            TextField(value = questionTitle, onValueChange = { questionTitle = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            // Descrição da Pergunta
//            Text("Descrição da Pergunta", fontSize = 16.sp)
//            TextField(value = questionDescription, onValueChange = { questionDescription = it })
//
//            Spacer(Modifier.height(16.dp))
//
//            Button(onClick = {
//                val db = FirebaseFirestore.getInstance()
//
//                val questionData = mapOf(
//                    "title" to questionTitle,
//                    "description" to questionDescription
//                )
//
//                db.collection("quizzes/$quizId/perguntas")
//                    .add(questionData)
//                    .addOnSuccessListener {
//                        navController.popBackStack() // Volta para a tela anterior
//                    }
//                    .addOnFailureListener {
//                        // Tratamento de erro
//                    }
//            }) {
//                Text("Salvar Pergunta")
//            }
//        }
//    }
//}


//package com.example.arteemfoco.screens.admin
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.Icon
//import androidx.compose.material.Text
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.arteemfoco.screens.quiz.Alternative
//
//@Composable
//fun QuizAddPerguntaAdminScreen(navController: NavController) {
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
//            text = "Nova Pergunta",
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
//            Text(text = "Audio Descrição", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
//            Text(text = "Alternativas", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//
//            // Perguntas Alternatives
//            Column(
//                modifier = Modifier.clickable { },
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Alternative("Quiz 1", "Descrição da pergunta 1")
//                Alternative("Quiz 2", "Descrição da pergunta 2")
//                Alternative("Quiz 3", "Descrição da pergunta 3")
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun QuizAddPerguntaAdminPreview() {
//    val navController = rememberNavController()
//    QuizAddPerguntaAdminScreen(navController)
//}

@Preview
@Composable
private fun QuizAddPerguntaAdminPreview() {
    val navController = rememberNavController()
    // Passar um valor de teste para o quizId
    val quizId = "testeQuizId"
    QuizAddPerguntaAdminScreen(navController, quizId)
}

