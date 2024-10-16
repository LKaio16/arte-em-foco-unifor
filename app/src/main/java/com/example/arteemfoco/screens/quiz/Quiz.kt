package com.example.arteemfoco.screens.quiz

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun QuizScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fundo branco para toda a tela
    ) {
        Text(
            text = "Quiz",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp) // Espaçamento do topo
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp) // Adiciona espaço suficiente para o título
        ) {
            // Imagem
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .size(250.dp, 150.dp)
            )

            // Pergunta
            Column(
                modifier = Modifier
                    .width(350.dp)
                    .padding(16.dp), // Espaçamento interno
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento entre os textos
            ) {

                Text(
                    text = "Pergunta 1",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = "Autor Genérico",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )


                Text(
                    text = "Korem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra?",
                    fontSize = 14.sp, textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(20.dp))

            // Alternativas
            Column(modifier = Modifier.clickable {navController.navigate("quizEndScreen")  }) {
                Text(text = "Alternativas", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                Alternative("Alternativa 1", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
                Spacer(Modifier.height(10.dp))
                Alternative("Alternativa 2", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
                Spacer(Modifier.height(10.dp))
                Alternative("Alternativa 3", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
                Spacer(Modifier.height(10.dp))
                Alternative("Alternativa 4", "Vorem ipsum dolor sit amet, consectetur adipiscing elit.")
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
@Preview
fun QuizScreenPreview() {
    val navController = rememberNavController()
    QuizScreen(navController)
}

@Composable
fun Alternative(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .width(300.dp) // Largura total da caixa principal
            .background(
                Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .height(70.dp)
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
}
