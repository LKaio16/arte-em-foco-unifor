package com.example.arteemfoco.screens.quiz

import QuizViewModel
import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if (quiz != null) {
            var currentQuestionIndex by remember { mutableStateOf(0) }
            var selectedIndex by remember { mutableStateOf<Int?>(null) }
            var correctAnswers by remember { mutableStateOf(0) }
            val question = quiz!!.perguntas.getOrNull(currentQuestionIndex)

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título do Quiz
                Text(
                    text = quiz!!.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (question != null) {
                    // Pergunta
                    Text(
                        text = question.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Alternativas
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

                    Spacer(Modifier.height(40.dp))

                    // Botão para ouvir pergunta
                    Button(
                        onClick = { speakQuestionAndAlternatives(textToSpeech, question) },
                        modifier = Modifier
                            .width(250.dp)
                            .height(48.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Text("Ouvir Pergunta", fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        } else {
            Text(
                text = "Carregando...",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Alternative(title: String, subtitle: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    Spacer(Modifier.height(12.dp))
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
