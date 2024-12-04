package com.example.arteemfoco.screens.quiz

import QuizViewModel
import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.arteemfoco.Screen
import com.example.arteemfoco.screens.admin.Question


import java.util.Locale
import androidx.compose.ui.platform.LocalContext


@Composable
fun QuizScreen(navController: NavController, quizViewModel: QuizViewModel, quizId: String, userName: String) {
    val context = LocalContext.current
    var textToSpeech by remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(Unit) {
        textToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                val locale = Locale("pt", "BR")
                textToSpeech?.setLanguage(locale)
            }
        }
    }

    LaunchedEffect(Unit) {
        quizViewModel.loadQuiz(quizId)
    }

    val quiz by quizViewModel.quiz.collectAsState()

    if (quiz != null) {
        var currentQuestionIndex by remember { mutableStateOf(0) }
        var selectedIndex by remember { mutableStateOf<Int?>(null) }
        var correctAnswers by remember { mutableStateOf(0) }
        val question = quiz!!.perguntas.getOrNull(currentQuestionIndex)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(quiz!!.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            if (question != null) {
                Text(question.title, fontSize = 18.sp, fontWeight = FontWeight.Medium)

                question.alternatives.forEachIndexed { index, alternative ->
                    Alternative(
                        title = "Alternativa ${index + 1}",
                        subtitle = alternative,
                        isSelected = selectedIndex == index,
                        onClick = {
                            if (selectedIndex == null) {
                                selectedIndex = index

                                if (index == question.correctAnswerIndex) {
                                    correctAnswers++
                                }

                                if (currentQuestionIndex == quiz!!.perguntas.size - 1) {
                                    // Salvar respostas no banco de dados
                                    quizViewModel.saveResults(userName, correctAnswers)
                                    navController.navigate("${Screen.QuizEnd.route}/${correctAnswers}/${quiz!!.perguntas.size}")
                                } else {
                                    selectedIndex = null
                                    currentQuestionIndex++
                                }
                            }
                        }
                    )
                }

                Button(
                    onClick = {
                        speakQuestionAndAlternatives(textToSpeech, question)
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Ouvir Pergunta")
                }
            }
        }
    } else {
        Text("Carregando...")
    }
}


// Função para falar a pergunta e as alternativas
fun speakQuestionAndAlternatives(tts: TextToSpeech?, question: Question) {

    val textToSpeak = buildString {
        append(question.title)
        question.alternatives.forEachIndexed { index, alternative ->
            append("\nAlternativa ${index + 1}: $alternative")
        }
    }
    tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
}

@Composable
fun Alternative(title: String, subtitle: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .background(
                if (isSelected) Color.Green else Color.Gray, // Alterar a cor quando selecionado
                shape = RoundedCornerShape(16.dp)
            )
            .height(70.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize() // Preencher a caixa cinza
        ) {
            // Caixa escura à esquerda
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp) // Largura da caixa escura
                    .background(
                        Color.DarkGray,
                        shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                    ) // Cor de fundo escura e bordas arredondadas
            )

            // Coluna com título e subtítulo à direita
            Column(
                modifier = Modifier
                    .fillMaxSize() // Preencher o restante do espaço
                    .padding(start = 16.dp), // Espaçamento entre a caixa escura e a coluna
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    color = Color.White,
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = Color.White,
                )
            }
        }
    }

    Spacer(Modifier.height(10.dp))
}
