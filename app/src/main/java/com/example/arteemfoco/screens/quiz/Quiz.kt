package com.example.arteemfoco.screens.quiz

import QuizViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.arteemfoco.Screen


@Composable
fun QuizScreen(navController: NavController, quizViewModel: QuizViewModel, quizId: String) {
    // Carrega o quiz do Firebase ao abrir a tela
    LaunchedEffect(Unit) {
        quizViewModel.loadQuiz(quizId)
    }

    val quiz by quizViewModel.quiz.collectAsState()

    if (quiz != null) {
        val question = quiz!!.perguntas.firstOrNull() // Exibe a primeira pergunta como exemplo
        var selectedIndex by remember { mutableStateOf<Int?>(null) } // Armazena o índice da alternativa selecionada

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
                                if (index == question.correctAnswerIndex) {
                                    // Navegar para a tela final se a resposta estiver certa
                                    navController.navigate(Screen.QuizEnd.route)
                                } else {
                                    // Caso a resposta esteja errada, você pode dar um feedback (exemplo: alterar cor)
                                }
                            }
                        }
                    )
                }
            }
        }
    } else {
        Text("Carregando...")
    }
}

@Composable
fun Alternative(title: String, subtitle: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(300.dp) // Largura total da caixa principal
            .background(
                if (isSelected) Color.Green else Color.Gray, // Alterar a cor quando selecionado
                shape = RoundedCornerShape(16.dp)
            )
            .height(70.dp)
            .clickable { onClick() } // Adiciona a ação de clique
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






data class Question(
    val title: String = "",
    val description: String = "",
    val alternatives: List<String> = listOf(),
    val correctAnswerIndex: Int = -1
)






