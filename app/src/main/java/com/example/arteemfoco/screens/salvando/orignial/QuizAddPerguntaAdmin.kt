package com.example.arteemfoco.screens.salvando.orignial

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.arteemfoco.screens.quiz.Alternative

@Composable
fun QuizAddPerguntaAdminScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fundo branco para toda a tela
    ) {
        // Ícone de voltar no canto superior esquerdo
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

        // Texto "Salvar" no canto superior direito
        Text(
            text = "Salvar",
            fontSize = 16.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 37.dp, end = 17.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Text(
            text = "Nova Pergunta",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            textAlign = TextAlign.Center
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
                    .size(300.dp, 150.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Labels e Caixas de Texto
            Text(text = "Título", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            // Simulação de uma caixa de texto
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 40.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(text = "Audio Descrição", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            // Simulação de um dropdown
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 40.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(text = "Alternativas", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            // Perguntas Alternatives
            Column(
                modifier = Modifier.clickable { },
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Alternative("Quiz 1", "Descrição da pergunta 1")
                Alternative("Quiz 2", "Descrição da pergunta 2")
                Alternative("Quiz 3", "Descrição da pergunta 3")
            }
        }
    }
}

@Preview
@Composable
private fun QuizAddPerguntaAdminPreview() {
    val navController = rememberNavController()
    QuizAddPerguntaAdminScreen(navController)
}
