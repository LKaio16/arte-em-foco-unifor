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
fun QuizScreen(navController: NavController, quizViewModel: QuizViewModel, quizId: String) {
    // Inicializa o TextToSpeech
    val context = LocalContext.current
    var textToSpeech by remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(Unit) {
        textToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                // Defina explicitamente o idioma como Português do Brasil
                val locale = Locale("pt", "BR")
                val result = textToSpeech?.setLanguage(locale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Trate o erro caso o idioma não seja suportado ou falte dados
                    // Por exemplo, exiba uma mensagem ou faça um fallback para o idioma padrão
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        textToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.getDefault()
            }
        }
    }

    // Carrega o quiz do Firebase ao abrir a tela
    LaunchedEffect(Unit) {
        quizViewModel.loadQuiz(quizId)
    }

    val quiz by quizViewModel.quiz.collectAsState()

    if (quiz != null) {
        var currentQuestionIndex by remember { mutableStateOf(0) } // Índice da pergunta atual
        var selectedIndex by remember { mutableStateOf<Int?>(null) } // Índice da alternativa selecionada
        var correctAnswers by remember { mutableStateOf(0) } // Número de respostas corretas
        val question = quiz!!.perguntas.getOrNull(currentQuestionIndex) // Pega a pergunta atual

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
                        isSelected = selectedIndex == index, // Verifica se a alternativa foi selecionada
                        onClick = {
                            if (selectedIndex == null) { // Só permite clicar se nenhuma alternativa foi selecionada
                                selectedIndex = index

                                // Incrementa a pontuação se a resposta estiver correta
                                if (index == question.correctAnswerIndex) {
                                    correctAnswers++
                                }

                                // Verifica se é a última pergunta
                                if (currentQuestionIndex == quiz!!.perguntas.size - 1) {
                                    // Navega para a tela final, passando o número de respostas corretas
                                    navController.navigate("${Screen.QuizEnd.route}/${correctAnswers}/${quiz!!.perguntas.size}")
                                } else {
                                    // Avança para a próxima pergunta
                                    selectedIndex = null // Reseta a seleção para a próxima pergunta
                                    currentQuestionIndex++
                                }
                            }
                        }
                    )
                }

                // Botão de Text-to-Speech
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
